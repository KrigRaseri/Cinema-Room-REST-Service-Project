package com.umbrella.cinemarestservice.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Represents information about a cinema, including the number of rows, columns, and available seats.
 *
 * This class is used to store and provide information about the cinema layout.
 * It includes the total number of rows and columns, as well as a list of available seats.
 *
 * @author KrigRaseri (pen name)
 * @version 1.0
 */
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
public class CinemaInfo {

    /**
     * Total number of rows in the cinema.
     */
    @Value("${cinema.rows}")
    private int totalRows;

    /**
     * Total number of columns in the cinema.
     */
    @Value("${cinema.cols}")
    private int totalColumns;

    /**
     * List of available seats in the cinema.
     */
    @Setter
    private List<Seat> seats;
}
