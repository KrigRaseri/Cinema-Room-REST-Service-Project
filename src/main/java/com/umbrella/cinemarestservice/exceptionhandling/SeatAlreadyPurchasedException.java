package com.umbrella.cinemarestservice.exceptionhandling;

/**
 * Exception thrown when a seat is already purchased.
 */
public class SeatAlreadyPurchasedException extends RuntimeException {
    public SeatAlreadyPurchasedException() {
        super("The ticket has been already purchased!");
    }
}
