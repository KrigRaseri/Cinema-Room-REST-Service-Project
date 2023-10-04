package com.umbrella.cinemarestservice.exceptionhandling;

/**
 * Exception thrown when a UUID token is wrong.
 */
public class WrongTokenException extends RuntimeException {
    public WrongTokenException() {
        super("Wrong token!");
    }
}
