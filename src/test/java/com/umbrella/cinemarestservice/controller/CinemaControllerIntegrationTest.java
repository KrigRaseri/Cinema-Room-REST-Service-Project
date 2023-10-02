package com.umbrella.cinemarestservice.controller;

import com.umbrella.cinemarestservice.dto.ReturnedTicketResponse;
import com.umbrella.cinemarestservice.dto.PurchaseTicketResponse;
import com.umbrella.cinemarestservice.dto.TokenRequest;
import com.umbrella.cinemarestservice.exceptionhandling.WrongTokenException;
import com.umbrella.cinemarestservice.model.*;
import com.umbrella.cinemarestservice.persistence.SeatRepository;
import com.umbrella.cinemarestservice.exceptionhandling.SeatNotFoundException;
import com.umbrella.cinemarestservice.exceptionhandling.SeatOutOfBoundsException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CinemaControllerIntegrationTest {

    private final WebTestClient webTestClient;
    private final SeatRepository seatRepository;

    @Autowired
    public CinemaControllerIntegrationTest(WebTestClient webTestClient, SeatRepository seatRepository) {
        this.webTestClient = webTestClient;
        this.seatRepository = seatRepository;
    }

    //To make sure the get test works for any set size.
    @Value("${cinema.totalRows}")
    int row;

    @Value("${cinema.totalColumns}")
    int column;

    //GET tests
    @Test
    void testGetCinemaInfo_ReturnsAllAvailableSeats() {
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

    //Purchase tests
    @Test
    void testPurchaseTicket_WhenSeatIsAvailable_ReturnsPurchasedTicket() {
        //Arrange
        Seat testSeat = new Seat(5, 5, 8);

        //Act
        webTestClient.put().uri("/purchase")
                .bodyValue(testSeat)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PurchaseTicketResponse.class)
                .value(purchaseTicketResponse -> {
                    assertThat(purchaseTicketResponse.token()).isNotNull();
                    assertThat(purchaseTicketResponse.ticket().row()).isEqualTo(5);
                    assertThat(purchaseTicketResponse.ticket().column()).isEqualTo(5);
                    assertThat(purchaseTicketResponse.ticket().price()).isEqualTo(8);
                });

        //Assert
        Seat seatAfterPurchase = seatRepository.findByRowAndColumn(5, 5).get();
        assertThat(seatAfterPurchase.isSeatAvailable()).isEqualTo(false);
        assertThat(seatAfterPurchase.getUUID()).isNotEqualTo("null");
    }

    @Test
    void testPurchaseTicket_WhenSeatIsNotAvailable_ReturnsSeatNotFoundException() {
        //Arrange
        Seat testSeat = seatRepository.findById(45L).orElseThrow(() -> new IllegalStateException("Seat not found"));
        testSeat.setSeatAvailable(false);
        seatRepository.saveAndFlush(testSeat);

        //Act
        webTestClient.put().uri("/purchase")
                .bodyValue(testSeat)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(SeatNotFoundException.class)
                .value(seatNotFoundException -> assertThat(seatNotFoundException.getMessage())
                        .isEqualTo("The ticket has been already purchased!"));
    }

    @Test
    void testPurchaseTicket_WhenSeatIsOutOfBounds_ReturnsSeatOutOfBoundsException() {
        webTestClient.put().uri("/purchase")
                .bodyValue(new Seat(0, 0, 10))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(SeatOutOfBoundsException.class)
                .value(seatOutOfBoundsException -> assertThat(seatOutOfBoundsException.getMessage())
                        .isEqualTo("The number of a row or a column is out of bounds!"));
    }

    //Return tests
    @Test
    void testReturnTicket_WhenTokenIsValid_ReturnsTicket() {
        //Arrange
        String uuidFromTest = seatRepository.findByRowAndColumn(5, 5).get().getUUID();
        TokenRequest tokenRequest = new TokenRequest(uuidFromTest);

        //Act
        webTestClient.put().uri("/return")
                .bodyValue(tokenRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ReturnedTicketResponse.class)
                .value(returnedTicketResponse -> {
                    assertThat(returnedTicketResponse.returned_ticket().row()).isEqualTo(5);
                    assertThat(returnedTicketResponse.returned_ticket().column()).isEqualTo(5);
                    assertThat(returnedTicketResponse.returned_ticket().price()).isEqualTo(8);
                });

        //Assert
        Seat seatAfterPurchase = seatRepository.findByRowAndColumn(5, 5).get();
        assertThat(seatAfterPurchase.isSeatAvailable()).isEqualTo(true);
        assertThat(seatAfterPurchase.getUUID()).isEqualTo("null");
    }

    @Test
    void testReturnTicket_WhenTokenIsInvalid_ReturnsWrongTokenException() {
        //Arrange
        TokenRequest tokenRequest = new TokenRequest("invalid token");

        //Act
        webTestClient.put().uri("/return")
                .bodyValue(tokenRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(WrongTokenException.class)
                .value(wrongTokenException -> assertThat(wrongTokenException.getMessage())
                        .isEqualTo("Wrong token!"));

    }
}



   /*
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    void testGetCinemaInfo_ReturnsAllAvailableSeats() {
        ResponseEntity<CinemaInfo> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/seats", CinemaInfo.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(9, response.getBody().getTotalRows());
        assertEquals(9, response.getBody().getTotalColumns());
        assertNotNull(response.getBody().getAvailableSeats());
    }

    @Test
    void testPurchaseTicket_WhenSeatIsAvailable_ShouldPurchaseSeatSuccessfully() {
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