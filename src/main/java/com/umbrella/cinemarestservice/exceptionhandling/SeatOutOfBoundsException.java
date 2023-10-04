package com.umbrella.cinemarestservice.exceptionhandling;

/**
 * Exception thrown when a seat is out of bounds.
 */
public class SeatOutOfBoundsException extends RuntimeException {
    public SeatOutOfBoundsException() {
        super("The number of a row or a column is out of bounds!");
    }
}

