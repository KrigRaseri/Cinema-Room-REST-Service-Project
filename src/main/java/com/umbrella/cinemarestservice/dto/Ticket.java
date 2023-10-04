package com.umbrella.cinemarestservice.dto;

/**
 * Ticket class is a dto representation of a seat entity.
 *
 * @param row The row number of the seat.
 * @param column The column number of the seat.
 * @param price The price of the seat.
 */
public record Ticket(int row, int column, int price) {
}
