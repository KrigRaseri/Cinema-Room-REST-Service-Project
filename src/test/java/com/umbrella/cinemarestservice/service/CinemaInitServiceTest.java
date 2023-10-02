package com.umbrella.cinemarestservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.List;

import com.umbrella.cinemarestservice.model.CinemaInfo;
import com.umbrella.cinemarestservice.model.Seat;
import com.umbrella.cinemarestservice.persistence.SeatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CinemaInitServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private CinemaInfo cinemaInfo;

    @InjectMocks
    private CinemaInitService cinemaInitService;

    @Test
    public void testRunWhenDatabaseIsEmpty() {
        //Arrange
        List<Seat> seats = cinemaInitService.generateAvailableSeats(9, 9);
        when(seatRepository.count()).thenReturn(0L);
        when(seatRepository.saveAll(any())).thenReturn(seats);

        //Act
        cinemaInitService.run();

        //Assert
        verify(seatRepository, times(1)).saveAll(any());
    }

    @Test
    public void testRunWhenDatabaseIsNotEmpty() {
        //Arrange
        when(seatRepository.count()).thenReturn(10L);

        //Act
        cinemaInitService.run();

        //Assert
        verify(seatRepository, never()).saveAll(any());
    }

    @Test
    public void testGenerateAvailableSeats() {
        //Act
        List<Seat> seats = cinemaInitService.generateAvailableSeats(9, 9);

        //Assert
        assertThat(seats).hasSize(81);
        assertThat(seats.get(0).getRow()).isEqualTo(1);
        assertThat(seats.get(0).getColumn()).isEqualTo(1);
        assertThat(seats.get(0).getPrice()).isEqualTo(10);
        assertThat(seats.get(69).getPrice()).isEqualTo(8);
    }

    @Test
    public void testDeterminePrice() {
        assertThat(cinemaInitService.determinePrice(1)).isEqualTo(10);
        assertThat(cinemaInitService.determinePrice(4)).isEqualTo(10);
        assertThat(cinemaInitService.determinePrice(7)).isEqualTo(8);
    }
}
