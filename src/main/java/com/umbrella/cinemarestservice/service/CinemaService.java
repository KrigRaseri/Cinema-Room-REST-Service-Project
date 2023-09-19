package com.umbrella.cinemarestservice.service;

import com.umbrella.cinemarestservice.domain.CinemaInfo;
import com.umbrella.cinemarestservice.domain.Seat;
import com.umbrella.cinemarestservice.web.exceptionhandling.SeatNotFoundException;
import com.umbrella.cinemarestservice.web.exceptionhandling.SeatOutOfBoundsException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Getter
@NoArgsConstructor
public class CinemaService {

    private final int TOTAL_ROWS = 9;
    private final int TOTAL_COLUMNS = 9;

    private final CinemaInfo cinemaInfo = new CinemaInfo(TOTAL_ROWS, TOTAL_COLUMNS);

    public Seat purchaseSeat(int row, int column) {
        if (row < 1 || row > cinemaInfo.getTotalRows() || column < 1 || column > cinemaInfo.getTotalColumns()) {
            throw new SeatOutOfBoundsException();
        }

        Seat seat = cinemaInfo.getAvailableSeats().stream()
                .filter(s -> s.row() == row && s.column() == column)
                .findFirst()
                .orElseThrow(SeatNotFoundException::new);

        cinemaInfo.getAvailableSeats().remove(seat);
        return seat;
    }
}

