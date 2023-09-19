package com.umbrella.cinemarestservice.web.controller;

import com.umbrella.cinemarestservice.domain.CinemaInfo;
import com.umbrella.cinemarestservice.domain.Seat;
import com.umbrella.cinemarestservice.service.CinemaService;
import com.umbrella.cinemarestservice.web.exceptionhandling.SeatNotFoundException;
import com.umbrella.cinemarestservice.web.exceptionhandling.SeatOutOfBoundsException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CinemaControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CinemaService cinemaService;

    @Test
    void testGetSeats() {
        int row = cinemaService.getTOTAL_ROWS();
        int column = cinemaService.getTOTAL_COLUMNS();

        webTestClient.get().uri("/seats")
                .exchange()
                .expectStatus().isOk()
                .expectBody(CinemaInfo.class)
                .value(cinemaInfo -> {
                    assertThat(cinemaInfo.getTotalRows()).isEqualTo(row);
                    assertThat(cinemaInfo.getTotalColumns()).isEqualTo(column);
                    assertThat(cinemaInfo.getAvailableSeats().size()).isEqualTo(row * column);
                    assertNotNull(cinemaInfo.getAvailableSeats());
                });
    }

    @Test
    void testPurchaseSeat_whenSeatIsAvailable() {
        webTestClient.post().uri("/purchase")
                .bodyValue(new Seat(1, 1, 10))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Seat.class)
                .value(seat -> {
                    assertThat(seat.row()).isEqualTo(1);
                    assertThat(seat.column()).isEqualTo(1);
                    assertThat(seat.price()).isEqualTo(10);
                });
    }

    @Test
    void testPurchaseSeat_whenSeatIsNotAvailable() {
        webTestClient.post().uri("/purchase")
                .bodyValue(new Seat(1, 6, 10))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Seat.class);

        webTestClient.post().uri("/purchase")
                .bodyValue(new Seat(1, 6, 10))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(SeatNotFoundException.class);
    }

    @Test
    void testPurchaseSeat_whenSeatIsOutOfBounds() {
        webTestClient.post().uri("/purchase")
                .bodyValue(new Seat(0, 0, 10))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(SeatOutOfBoundsException.class);
    }
}



   /*
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    void testGetSeats() {
        ResponseEntity<CinemaInfo> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/seats", CinemaInfo.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(9, response.getBody().getTotalRows());
        assertEquals(9, response.getBody().getTotalColumns());
        assertNotNull(response.getBody().getAvailableSeats());
    }

    @Test
    void testPurchaseSeat_whenSeatIsAvailable() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Seat> request = new HttpEntity<>(new Seat(1, 1, 10), headers);

        ResponseEntity<Seat> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/purchase", request, Seat.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().row()).isEqualTo(1);
        assertThat(response.getBody().column()).isEqualTo(1);
        assertThat(response.getBody().price()).isEqualTo(10);
    }*/