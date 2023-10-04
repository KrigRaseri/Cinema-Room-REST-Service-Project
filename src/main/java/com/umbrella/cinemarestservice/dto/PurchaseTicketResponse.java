package com.umbrella.cinemarestservice.dto;

/**
 * Represents a response for purchasing a cinema ticket.
 *
 * This class contains information about the token associated with the purchased ticket
 * and details of the purchased ticket itself.
 *
 * @param token The unique token associated with the purchased ticket.
 * @param ticket Details of the purchased cinema ticket.
 */
public record PurchaseTicketResponse(String token, Ticket ticket) {
}
