package com.umbrella.cinemarestservice.controller;

import com.umbrella.cinemarestservice.dto.ReturnedTicketResponse;
import com.umbrella.cinemarestservice.dto.PurchaseTicketResponse;
import com.umbrella.cinemarestservice.dto.TokenRequest;
import com.umbrella.cinemarestservice.model.*;
import com.umbrella.cinemarestservice.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CinemaController {

    private final CinemaService cinemaService;

    @GetMapping("/seats")
    public CinemaInfo getCinemaInfo() {
        return cinemaService.getCinemaInfo();
    }

    @PutMapping("/purchase")
    public ResponseEntity<PurchaseTicketResponse> purchaseTicket(@RequestBody Seat seat) {
        PurchaseTicketResponse purchaseTicketResponse = cinemaService.purchaseSeat(seat);
        return ResponseEntity.ok(purchaseTicketResponse);
    }

    @PutMapping("/return")
    public ResponseEntity<ReturnedTicketResponse> returnTicket(@RequestBody TokenRequest token) {
        ReturnedTicketResponse returnResponse = cinemaService.returnTicket(token.token());
        return ResponseEntity.ok(returnResponse);
    }
}
