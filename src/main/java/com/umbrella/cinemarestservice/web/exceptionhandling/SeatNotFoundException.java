package com.umbrella.cinemarestservice.web.exceptionhandling;

public class SeatNotFoundException extends RuntimeException {
    public SeatNotFoundException() {
        super("The ticket has been already purchased!");
    }
}
