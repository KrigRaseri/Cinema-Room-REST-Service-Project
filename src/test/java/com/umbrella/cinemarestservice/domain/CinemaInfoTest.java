package com.umbrella.cinemarestservice.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class CinemaInfoTest {

    @Test
    public void generateAvailableSeatsTest() {
        CinemaInfo cinemaInfo = new CinemaInfo(9, 9);
        List<Seat> seats = cinemaInfo.getAvailableSeats();

        assertThat(seats.size()).isEqualTo(81);
    }

    @Test
    void testJsonPropertyAnnotations() throws JsonProcessingException, JSONException {
        String expectedJson = "{\"total_rows\": 0, \"total_columns\": 0, \"available_seats\": []}";
        ObjectMapper objectMapper = new ObjectMapper();
        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(new CinemaInfo(0, 0)), true);
    }
}
