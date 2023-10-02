package com.umbrella.cinemarestservice.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.ArrayList;
import java.util.List;

public class CinemaInfoTest {

    @Test
    void testJsonPropertyAnnotations() throws JsonProcessingException, JSONException {
        //Arrange
        String expectedJson = "{\"total_rows\": 0, \"total_columns\": 0, \"available_seats\": []}";
        ObjectMapper objectMapper = new ObjectMapper();
        List<Seat> seats = new ArrayList<>();

        //Assert
        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(new CinemaInfo(0, 0, seats)), true);
    }
}
