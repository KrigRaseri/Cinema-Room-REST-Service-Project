package com.umbrella.cinemarestservice.dto;

/**
 * Represents a request for a UUID token.
 *
 * @param token The UUID token.
 */
public record TokenRequest(String token) {
}
