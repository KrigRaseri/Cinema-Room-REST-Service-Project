package com.umbrella.cinemarestservice.controller;

import com.umbrella.cinemarestservice.dto.ReturnedTicketResponse;
import com.umbrella.cinemarestservice.dto.Ticket;
import com.umbrella.cinemarestservice.dto.PurchaseTicketResponse;
import com.umbrella.cinemarestservice.dto.TokenRequest;
import com.umbrella.cinemarestservice.exceptionhandling.WrongTokenException;
import com.umbrella.cinemarestservice.model.*;
import com.umbrella.cinemarestservice.service.CinemaService;
import com.umbrella.cinemarestservice.exceptionhandling.SeatNotFoundException;
import com.umbrella.cinemarestservice.exceptionhandling.SeatOutOfBoundsException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CinemaControllerTest {

    @InjectMocks
    private CinemaController cinemaController;

    @Mock
    private CinemaService cinemaService;

    @Test
    public void testGetCinemaInfo_ShouldReturn81Seats() {
        // Arrange
        List<Seat> seats = new ArrayList<>();
        IntStream.range(0, 81).forEach(s -> seats.add(new Seat(1, 1, 10)));
        CinemaInfo cinemaInfo = new CinemaInfo(9, 9, seats);
        when(cinemaService.getCinemaInfo()).thenReturn(cinemaInfo);

        // Act
        CinemaInfo result = cinemaService.getCinemaInfo();

        // Assert
        assertThat(result.getAvailableSeats().size()).isEqualTo(81);
    }

    @Test
    public void testPurchaseTicket_WhenSeatIsAvailable_ShouldPurchaseSeatSuccessfully() {
        // Arrange
        Seat expectedSeat = new Seat(1, 1, 10);
        Ticket ticket = new Ticket(1, 1, 10);
        String pseudoUUID = "1234-1234-1234-1234";
        expectedSeat.setUUID(pseudoUUID);
        PurchaseTicketResponse expectedPurchaseTicketResponse = new PurchaseTicketResponse(pseudoUUID, ticket);
        when(cinemaService.purchaseSeat(expectedSeat)).thenReturn(expectedPurchaseTicketResponse);

        // Act
        ResponseEntity<?> responseEntity = cinemaController.purchaseTicket(expectedSeat);

        // Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(expectedPurchaseTicketResponse);
    }

    @Test
    public void testPurchaseSeat_WhenPurchasingUnavailableSeat_ShouldThrowSeatNotFoundException() {
        // Arrange
        Seat seat = new Seat(1, 1, 10);
        when(cinemaService.purchaseSeat(seat)).thenThrow(new SeatNotFoundException());

        // Act and Assert
        assertThatThrownBy(() -> cinemaController.purchaseTicket(seat))
                .isInstanceOf(SeatNotFoundException.class)
                .hasMessage("The ticket has been already purchased!");
    }

    @Test
    public void testPurchaseTicket_ShouldThrowSeatOutOfBoundsException() {
        // Arrange
        Seat seat = new Seat(10, 10, 10);
        when(cinemaService.purchaseSeat(seat)).thenThrow(new SeatOutOfBoundsException());

        // Act
        assertThatThrownBy(() -> cinemaController.purchaseTicket(seat))
                .isInstanceOf(SeatOutOfBoundsException.class)
                .hasMessage("The number of a row or a column is out of bounds!");
    }

    @Test
    public void testReturnTicket_WhenTokenIsValid_ReturnsTicket() {
        // Arrange
        Ticket ticket = new Ticket(1, 1, 10);
        String pseudoUUID = "1234-1234-1234-1234";
        ReturnedTicketResponse expectedTicketResponse = new ReturnedTicketResponse(ticket);
        TokenRequest tokenRequest = new TokenRequest(pseudoUUID);
        when(cinemaService.returnTicket(tokenRequest.token())).thenReturn(expectedTicketResponse);

        // Act
        ResponseEntity<?> responseEntity = cinemaController.returnTicket(tokenRequest);

        // Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(expectedTicketResponse);
    }

    @Test
    void testReturnTicket_WhenWrongToken_ReturnWrongTokenException() {
        // Arrange
        TokenRequest tokenRequest = new TokenRequest("invalid token");
        when(cinemaService.returnTicket("invalid token")).thenThrow(new WrongTokenException());

        // Act
        assertThatThrownBy(() -> cinemaController.returnTicket(tokenRequest))
                .isInstanceOf(WrongTokenException.class)
                .hasMessage("Wrong token!");
    }
}