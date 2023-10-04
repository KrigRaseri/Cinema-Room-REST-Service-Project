package com.umbrella.cinemarestservice.exceptionhandling;

/**
 * Exception thrown when a password is wrong.
 */
public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException() {
        super("The password is wrong!");
    }
}
