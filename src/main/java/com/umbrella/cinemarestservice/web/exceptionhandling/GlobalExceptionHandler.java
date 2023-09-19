package com.umbrella.cinemarestservice.web.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SeatNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleSeatNotFoundException(SeatNotFoundException e) {
        return new ResponseEntity<>(
                new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage())
                , HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(SeatOutOfBoundsException.class)
    public ResponseEntity<CustomErrorResponse> customHandleSeatOutOfBound(Exception e) {
        return new ResponseEntity<>(
                new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage())
                , HttpStatus.BAD_REQUEST
        );
    }
}
