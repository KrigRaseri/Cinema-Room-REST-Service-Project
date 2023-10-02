package com.umbrella.cinemarestservice.exceptionhandling;

public class WrongTokenException extends RuntimeException {
    public WrongTokenException() {
        super("Wrong token!");
    }
}
