package com.umbrella.cinemarestservice.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor
public class CinemaInfoDTO {
    @JsonProperty(value = "total_rows")
    private int totalRows;

    @JsonProperty(value = "total_columns")
    private int totalColumns;

    @JsonProperty(value = "available_seats")
    private List<Seat> availableSeats;

    public CinemaInfoDTO(int rows, int columns) {
        this.totalRows = rows;
        this.totalColumns = columns;
        this.availableSeats = generateAvailableSeats(rows, columns);
    }

    private List<Seat> generateAvailableSeats(int totalRows, int totalColumns) {
        return IntStream.rangeClosed(1, totalRows)
                .boxed()
                .flatMap(row -> IntStream.rangeClosed(1, totalColumns)
                        .mapToObj(column -> new Seat(row, column)))
                .collect(Collectors.toList());
    }
}
