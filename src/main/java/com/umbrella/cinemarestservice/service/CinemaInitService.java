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

/**
 * Service class responsible for initializing the cinema database with available seats.
 * Implements the CommandLineRunner interface to run database initialization at application startup.
 *
 * This service generates and saves seat entities to the database if no seats are found.
 * The seat prices are determined based on the row number.
 *
 * @author KrigRaseri (pen name)
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CinemaInitService implements CommandLineRunner {

    private final SeatRepository seatRepository;

    /**
     * Total number of rows in the cinema. Total rows is set in the application.properties file.
     */
    @Value("${cinema.totalRows}")
    private int cinemaRows;

    /**
     * Total number of columns in the cinema. Total columns is set in the application.properties file.
     */
    @Value("${cinema.totalColumns}")
    private int cinemaCols;

    /**
     * Runs database initialization at application startup. If database is empty it will fill it with seats.
     *
     * @param args Command line arguments.
     */
    @Override
    public void run(String... args) {
        log.info("Initializing the database...");

        if (seatRepository.count() == 0) {

            List<Seat> seats = generateAvailableSeats(cinemaRows, cinemaCols);
            seatRepository.saveAll(seats);

            log.info("{} seats saved to the database", seats.size());

        } else {
            log.info("Database already initialized");
        }

        log.info("Initialization complete.");
    }

    /**
     * Generates a list of available seats based on the specified total rows and columns.
     *
     * @param totalRows    The total number of rows in the cinema.
     * @param totalColumns The total number of columns in the cinema.
     * @return A list of available seat entities.
     */
    List<Seat> generateAvailableSeats(int totalRows, int totalColumns) {
        return IntStream.rangeClosed(1, totalRows)
                .boxed()
                .flatMap(row -> IntStream.rangeClosed(1, totalColumns)
                        .mapToObj(column -> new Seat(row, column, determinePrice(row))))
                .collect(Collectors.toList());
    }

    /**
     * Determines the price of a seat based on its row number.
     * Rows 1 to 4 have a higher price, while rows 5 and above have a lower price.
     *
     * @param row The row number of the seat.
     * @return The price of the seat.
     */
    int determinePrice(int row) {
        return row <= 4 ? 10 : 8;
    }
}