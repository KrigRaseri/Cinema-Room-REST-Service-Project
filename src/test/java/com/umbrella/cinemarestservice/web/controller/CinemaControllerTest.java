package com.umbrella.cinemarestservice.web.controller;

import com.umbrella.cinemarestservice.domain.CinemaInfo;
import com.umbrella.cinemarestservice.domain.Seat;
import com.umbrella.cinemarestservice.service.CinemaService;
import com.umbrella.cinemarestservice.web.exceptionhandling.SeatNotFoundException;
import com.umbrella.cinemarestservice.web.exceptionhandling.SeatOutOfBoundsException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CinemaControllerTest {

    @InjectMocks
    private CinemaController cinemaController;

    @Mock
    private CinemaService cinemaService;

    @Test
    public void testGetSeats() {
        // Arrange
        when(cinemaService.getCinemaInfo()).thenReturn(new CinemaInfo(9, 9));

        // Act
        ResponseEntity<CinemaInfo> responseEntity = cinemaController.getSeats();

        // Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getTotalRows()).isEqualTo(9);
        assertThat(responseEntity.getBody().getTotalColumns()).isEqualTo(9);
    }

    @Test
    public void testPurchaseSeat_whenSeatIsAvailable() {
        // Arrange
        int row = 1;
        int column = 1;
        Seat expectedSeat = new Seat(row, column, 10);
        when(cinemaService.purchaseSeat(row, column)).thenReturn(expectedSeat);

        // Act
        ResponseEntity<?> responseEntity = cinemaController.purchaseSeat(expectedSeat);

        // Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(expectedSeat);
    }

    @Test
    public void testPurchaseSeat_whenSeatIsNotAvailable() {
        // Arrange
        int row = 1;
        int column = 1;
        Seat seat = new Seat(row, column, 10);
        when(cinemaService.purchaseSeat(row, column)).thenThrow(new SeatNotFoundException());

        // Act
        try{
            ResponseEntity<?> responseEntity = cinemaController.purchaseSeat(seat);
        } catch (SeatNotFoundException e) {
            // Assert
            assertThat(e.getMessage()).isEqualTo("The ticket has been already purchased!");
        }
    }

    @Test
    public void testPurchaseSeat_whenSeatIsOutOfBounds() {
        // Arrange
        int row = 10;
        int column = 10;
        when(cinemaService.purchaseSeat(row, column)).thenThrow(new SeatOutOfBoundsException());

        // Act
        try {
            ResponseEntity<?> responseEntity = cinemaController.purchaseSeat(new Seat(row, column, 10));
        } catch (SeatOutOfBoundsException e) {
            // Assert
            assertThat(e.getMessage()).isEqualTo("The number of a row or a column is out of bounds!");
        }
    }
}