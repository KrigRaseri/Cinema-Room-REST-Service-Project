package com.umbrella.cinemarestservice.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class CinemaInfoDTOTest {

    @Test
    public void generateAvailableSeatsTest() {
        CinemaInfoDTO cinemaInfoDTO = new CinemaInfoDTO(9, 9);
        List<Seat> seats = cinemaInfoDTO.getAvailableSeats();

        assertThat(seats.size()).isEqualTo(81);
    }

    @Test
    void testJsonPropertyAnnotations() throws JsonProcessingException, JSONException {
        // Test that the JSON properties are correctly mapped
        // For example, using JSONAssert library
        String expectedJson = "{\"total_rows\": 0, \"total_columns\": 0, \"available_seats\": []}";
        ObjectMapper objectMapper = new ObjectMapper();
        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(new CinemaInfoDTO(0, 0)), true);
    }
}
