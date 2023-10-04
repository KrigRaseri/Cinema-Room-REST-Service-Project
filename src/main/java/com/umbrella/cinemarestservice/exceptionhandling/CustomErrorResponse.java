package com.umbrella.cinemarestservice.exceptionhandling;

/**
 * Custom error response to format the error message.
 */
public record CustomErrorResponse(int status, String error) {

}
