package com.umbrella.cinemarestservice.service;

import com.umbrella.cinemarestservice.dto.CinemaStatsResponse;
import com.umbrella.cinemarestservice.dto.PurchaseTicketResponse;
import com.umbrella.cinemarestservice.dto.ReturnedTicketResponse;
import com.umbrella.cinemarestservice.exceptionhandling.SeatAlreadyPurchasedException;
import com.umbrella.cinemarestservice.exceptionhandling.SeatOutOfBoundsException;
import com.umbrella.cinemarestservice.exceptionhandling.WrongPasswordException;
import com.umbrella.cinemarestservice.exceptionhandling.WrongTokenException;
import com.umbrella.cinemarestservice.mapper.SeatToTicketMapper;
import com.umbrella.cinemarestservice.model.CinemaInfo;
import com.umbrella.cinemarestservice.model.Seat;
import com.umbrella.cinemarestservice.persistence.SeatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service class responsible for cinema-related operations such as seat purchase and ticket return.
 * Provides methods for retrieving cinema information and statistics.
 *
 * @author KrigRaseri (pen name)
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CinemaService {

    private final SeatRepository seatRepository;

    /**
     * Total number of rows in the cinema. Total rows is set in the application.properties file.
     */
    @Value("${cinema.totalRows}")
    private int totalRows;

    /**
     * Total number of columns in the cinema. Total columns is set in the application.properties file.
     */
    @Value("${cinema.totalColumns}")
    private int totalColumns;

    //Funny lil mapper man.
    private final SeatToTicketMapper seatToTicketMapper = new SeatToTicketMapper();

    /**
     * Retrieves information about the cinema, including total rows, total columns, and available seats.
     *
     * @return CinemaInfo object containing cinema details.
     */
    public CinemaInfo getCinemaInfo() {
        List<Seat> availableSeats = seatRepository.findAll();
        return new CinemaInfo(totalRows, totalColumns, availableSeats);
    }

    /**
     * Retrieves statistics about the cinema, including income, available seats, and purchased seats.
     *
     * @param password The password required to access statistics.
     * @return CinemaStatsResponse object containing cinema statistics.
     * @throws WrongPasswordException if the provided password is incorrect.
     */
    public CinemaStatsResponse getStats(String password) {

        if(password == null || !password.equals("super_secret")) {
            throw new WrongPasswordException();
        }

        List<Seat> seatsList = seatRepository.findAll();
        int income = 0;
        int available = 0;
        int purchased = 0;

        for (Seat seat : seatsList) {
            if (!seat.isSeatAvailable()) {
                income += seat.getPrice();
                purchased++;
            } else {
                available++;
            }
        }
        return new CinemaStatsResponse(income, available, purchased);
    }

    /**
     * Attempts to purchase a seat and returns a PurchaseTicketResponse.
     *
     * @param seat The Seat object representing the seat to be purchased.
     * @return PurchaseTicketResponse object containing the purchase details.
     * @throws SeatOutOfBoundsException if the seat is out of bounds.
     * @throws SeatAlreadyPurchasedException  if the seat is not found in the database.
     */
    @Transactional
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
                    return new SeatAlreadyPurchasedException();
                });
    }

    /**
     * Attempts to return a ticket and returns a ReturnedTicketResponse.
     *
     * @param seatUUID The UUID of the seat to be returned.
     * @return ReturnedTicketResponse object containing the returned ticket details.
     * @throws WrongTokenException if the provided token is incorrect.
     */
    @Transactional
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
}

