package com.umbrella.cinemarestservice.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler for handling custom exceptions in the application.
 * Provides methods for handling specific exception types and returning appropriate HTTP responses.
 *
 * @author KrigRaseri (pen name)
 * @version 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles exceptions related to seat not found or seat out of bounds errors.
     *
     * @param e The runtime exception to handle.
     * @return ResponseEntity containing a custom error response and HTTP status code.
     */
    @ExceptionHandler({SeatAlreadyPurchasedException.class, SeatOutOfBoundsException.class, WrongTokenException.class})
    public ResponseEntity<CustomErrorResponse> handleSeatNotFoundAndOutOfBoundsException(RuntimeException e) {
        return new ResponseEntity<>(
                new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage())
                , HttpStatus.BAD_REQUEST
        );
    }

    /**
     * Handles exceptions related to invalid passwords.
     *
     * @param e The exception to handle.
     * @return ResponseEntity containing a custom error response and HTTP status code.
     */
    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<CustomErrorResponse> customHandleInvalidPassword(Exception e) {
        return new ResponseEntity<>(
                new CustomErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage())
                , HttpStatus.UNAUTHORIZED
        );
    }
}
