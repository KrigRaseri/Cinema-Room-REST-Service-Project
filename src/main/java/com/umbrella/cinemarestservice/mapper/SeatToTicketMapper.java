package com.umbrella.cinemarestservice.mapper;

import com.umbrella.cinemarestservice.dto.PurchaseTicketResponse;
import com.umbrella.cinemarestservice.dto.ReturnedTicketResponse;
import com.umbrella.cinemarestservice.dto.Ticket;
import com.umbrella.cinemarestservice.model.Seat;
import lombok.extern.slf4j.Slf4j;

/**
 * Mapper class to map a seat entity to a ticket dto, and then to the appropriate response dto.
 *
 * @author KrigRaseri (pen name)
 * @version 1.0
 */
@Slf4j
public class SeatToTicketMapper {
    public ReturnedTicketResponse mapToReturnedTicketResponse(Seat seat) {
        log.debug("Mapping Seat to ReturnedTicketResponse: {}", seat);

        Ticket ticket = mapToTicket(seat);
        ReturnedTicketResponse response = new ReturnedTicketResponse(ticket);

        log.debug("Mapped to ReturnedTicketResponse: {}", response);
        return response;
    }

    public PurchaseTicketResponse mapToPurchaseTicketResponse(String uuid, Seat seat) {
        log.debug("Mapping Seat to PurchaseTicketResponse");

        Ticket ticket = mapToTicket(seat);
        PurchaseTicketResponse response = new PurchaseTicketResponse(uuid, ticket);

        log.debug("Mapped to PurchaseTicketResponse success");
        return response;
    }

    private Ticket mapToTicket(Seat seat) {
        log.debug("Mapping Seat to Ticket: {}", seat);

        Ticket ticket = new Ticket(seat.getRow(), seat.getColumn(), seat.getPrice());

        log.debug("Mapped to Ticket: {}", ticket);
        return ticket;
    }
}
