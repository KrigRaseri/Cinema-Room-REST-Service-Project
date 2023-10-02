package com.umbrella.cinemarestservice.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({SeatNotFoundException.class, SeatOutOfBoundsException.class, WrongTokenException.class})
    public ResponseEntity<CustomErrorResponse> handleSeatNotFoundAndOutOfBoundsException(RuntimeException e) {
        return new ResponseEntity<>(
                new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage())
                , HttpStatus.BAD_REQUEST
        );
    }
}
