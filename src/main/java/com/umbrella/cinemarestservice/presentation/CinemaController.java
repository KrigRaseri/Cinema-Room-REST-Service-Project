package com.umbrella.cinemarestservice.presentation;

import com.umbrella.cinemarestservice.business.CinemaInfoDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CinemaController {

    @GetMapping("/seats")
    public CinemaInfoDTO getSeats() {
        int rows = 9;
        int columns = 9;
        return new CinemaInfoDTO(rows, columns);
    }
}
