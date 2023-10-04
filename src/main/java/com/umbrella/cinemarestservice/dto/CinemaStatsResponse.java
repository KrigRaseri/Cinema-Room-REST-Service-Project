package com.umbrella.cinemarestservice.dto;

/**
 * Represents a response containing cinema statistics.
 *
 * This class provides information about the cinema's financial income,
 * the number of available seats, and the number of seats that have been purchased.
 *
 * @param income The total income generated by the cinema.
 * @param available The total number of available seats in the cinema.
 * @param purchased The total number of purchased seats in the cinema.
 */
public record CinemaStatsResponse(int income, int available, int purchased) {
}
