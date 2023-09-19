package com.umbrella.cinemarestservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@NoArgsConstructor
@Component
public class CinemaInfo {

    @JsonProperty(value = "total_rows")
    private int totalRows;

    @JsonProperty(value = "total_columns")
    private int totalColumns;

    @JsonProperty(value = "available_seats")
    private List<Seat> availableSeats;

    public CinemaInfo(int rows, int columns) {
        this.totalRows = rows;
        this.totalColumns = columns;
        this.availableSeats = generateAvailableSeats(rows, columns);
    }

    private List<Seat> generateAvailableSeats(int totalRows, int totalColumns) {
        return IntStream.rangeClosed(1, totalRows)
                .boxed()
                .flatMap(row -> IntStream.rangeClosed(1, totalColumns)
                        .mapToObj(column -> new Seat(row, column, determinePrice(row))))
                .collect(Collectors.toList());
    }

    private int determinePrice(int row) {
        return row <= 4 ? 10 : 8;
    }
}
