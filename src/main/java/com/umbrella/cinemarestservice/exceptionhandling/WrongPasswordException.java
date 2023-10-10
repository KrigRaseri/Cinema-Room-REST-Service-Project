package com.umbrella.cinemarestservice.exceptionhandling;

import javax.naming.AuthenticationException;

/**
 * Exception thrown when a password is wrong.
 */
public class WrongPasswordException extends AuthenticationException {
    public WrongPasswordException() {
        super("The password is wrong!");
    }
}
