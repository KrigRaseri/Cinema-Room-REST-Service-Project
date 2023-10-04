package com.umbrella.cinemarestservice.dto;

/**
 * Represents a response for returning a cinema ticket.
 *
 * This class contains details of the returned ticket.
 *
 * @param returned_ticket Details of the cinema ticket that has been returned.
 */
public record ReturnedTicketResponse(Ticket returned_ticket) {
}
