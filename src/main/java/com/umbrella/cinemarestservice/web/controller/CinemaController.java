package com.umbrella.cinemarestservice.web.controller;

import com.umbrella.cinemarestservice.domain.CinemaInfo;
import com.umbrella.cinemarestservice.domain.Seat;
import com.umbrella.cinemarestservice.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CinemaController {

    private final CinemaService cinemaService;

    @GetMapping("/seats")
    public ResponseEntity<CinemaInfo> getSeats() {
        return ResponseEntity.ok(cinemaService.getCinemaInfo());
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSeat(@RequestBody Seat seat) {
        seat = cinemaService.purchaseSeat(seat.row(), seat.column());
        return ResponseEntity.ok(seat);
    }
}
