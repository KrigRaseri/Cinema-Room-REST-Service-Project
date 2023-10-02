package com.umbrella.cinemarestservice.service;

import com.umbrella.cinemarestservice.model.Seat;
import com.umbrella.cinemarestservice.persistence.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@Service
public class CinemaInitService implements CommandLineRunner {

    private final SeatRepository seatRepository;

    @Value("${cinema.totalRows}")
    private int cinemaRows;

    @Value("${cinema.totalColumns}")
    private int cinemaCols;

    @Override
    public void run(String... args) {
        log.info("Initializing the database...");

        if (seatRepository.count() == 0) {

            List<Seat> seats = generateAvailableSeats(cinemaRows, cinemaCols);
            seatRepository.saveAll(seats);

            log.info("{} seats saved to database", seats.size());

        } else {
            log.info("Database already initialized");
        }

        log.info("Initialization complete.");
    }

    List<Seat> generateAvailableSeats(int totalRows, int totalColumns) {
        return IntStream.rangeClosed(1, totalRows)
                .boxed()
                .flatMap(row -> IntStream.rangeClosed(1, totalColumns)
                        .mapToObj(column -> new Seat(row, column, determinePrice(row))))
                .collect(Collectors.toList());
    }

    int determinePrice(int row) {
        return row <= 4 ? 10 : 8;
    }
}
