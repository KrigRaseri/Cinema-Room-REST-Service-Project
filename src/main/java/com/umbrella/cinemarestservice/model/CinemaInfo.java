package com.umbrella.cinemarestservice.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
public class CinemaInfo {

    @Value("${cinema.rows}")
    private int totalRows;

    @Value("${cinema.cols}")
    private int totalColumns;

    @Setter
    private List<Seat> availableSeats;
}
