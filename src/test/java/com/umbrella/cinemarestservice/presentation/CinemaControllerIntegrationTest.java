package com.umbrella.cinemarestservice.presentation;


import com.umbrella.cinemarestservice.business.CinemaInfoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CinemaControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    void testGetSeats() {
        ResponseEntity<CinemaInfoDTO> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/seats", CinemaInfoDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(9, response.getBody().getTotalRows());
        assertEquals(9, response.getBody().getTotalColumns());
        assertNotNull(response.getBody().getAvailableSeats());
    }
}
