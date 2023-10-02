package com.umbrella.cinemarestservice.service;

import com.umbrella.cinemarestservice.dto.PurchaseTicketResponse;
import com.umbrella.cinemarestservice.dto.ReturnedTicketResponse;
import com.umbrella.cinemarestservice.mapper.SeatToTicketMapper;
import com.umbrella.cinemarestservice.model.CinemaInfo;
import com.umbrella.cinemarestservice.model.Seat;
import com.umbrella.cinemarestservice.exceptionhandling.WrongTokenException;
import com.umbrella.cinemarestservice.persistence.SeatRepository;
import com.umbrella.cinemarestservice.exceptionhandling.SeatNotFoundException;
import com.umbrella.cinemarestservice.exceptionhandling.SeatOutOfBoundsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class CinemaService {

    private final SeatRepository seatRepository;

    @Value("${cinema.totalRows}")
    private int totalRows;

    @Value("${cinema.totalColumns}")
    private int totalColumns;

    private final SeatToTicketMapper seatToTicketMapper = new SeatToTicketMapper();



    public CinemaInfo getCinemaInfo() {
        List<Seat> availableSeats = seatRepository.findAll();
        return new CinemaInfo(totalRows, totalColumns, availableSeats);
    }

    public PurchaseTicketResponse purchaseSeat(Seat seat) {
        int row = seat.getRow();
        int column = seat.getColumn();

        log.debug("Attempting to purchase seat at row {} and column {}", row, column);

        if (row < 1 || row > totalRows || column < 1 || column > totalColumns) {
            log.warn("Seat purchase failed: Seat is out of bounds. Row: {}, Column: {}", row, column);
            throw new SeatOutOfBoundsException();
        }

        return seatRepository
                .findByRowAndColumn(row, column)
                .filter(Seat::isSeatAvailable)
                .map(foundSeat -> {
                    foundSeat.setSeatAvailable(false);
                    foundSeat.setUUID(UUID.randomUUID().toString());
                    seatRepository.save(foundSeat);
                    log.debug("Seat purchased successfully: Row {}, Column {}", row, column);
                    return seatToTicketMapper.mapToPurchaseTicketResponse(foundSeat.getUUID(), foundSeat);
                })
                .orElseThrow(() -> {
                    log.warn("Seat purchase failed: Seat not available. Row: {}, Column: {}", row, column);
                    return new SeatNotFoundException();
                });
    }

    public ReturnedTicketResponse returnTicket(String seatUUID) {
        log.debug("Attempting to return ticket with submitted UUID");
        return seatRepository
                .findByUUID(seatUUID)
                .filter(seat -> !seat.isSeatAvailable())
                .map(seat1 -> {
                    seat1.setSeatAvailable(true);
                    seat1.setUUID("null");
                    seatRepository.save(seat1);
                    log.debug("Ticket returned successfully: Row {}, Column {}", seat1.getRow(), seat1.getColumn());
                    return seatToTicketMapper.mapToReturnedTicketResponse(seat1);
                })
                .orElseThrow(() -> {
                    log.warn("Ticket return failed: Wrong token");
                    return new WrongTokenException();
                });
    }

    public String deleteSeat(long seatID) {
        Seat seat = seatRepository.findById(seatID).orElseThrow(SeatNotFoundException::new);
        seatRepository.deleteById(seatID);
        return "Seat " + seat.getId() + " deleted";
    }
}

