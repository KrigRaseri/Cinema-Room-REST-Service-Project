package com.umbrella.cinemarestservice.controller;

import com.umbrella.cinemarestservice.dto.*;
import com.umbrella.cinemarestservice.model.*;
import com.umbrella.cinemarestservice.persistence.UserRepository;
import com.umbrella.cinemarestservice.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class that handles HTTP requests related to cinema operations.
 *
 * @author KrigRaseri (pen name)
 * @version 1.0
 */
@RequiredArgsConstructor
@RestController
public class CinemaController {

    private final CinemaService cinemaService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Endpoint to retrieve cinema information.
     *
     * @return CinemaInfo containing information about available seats.
     */
    @GetMapping("/seats")
    public CinemaInfo getCinemaInfo() {
        return cinemaService.getCinemaInfo();
    }

    /**
     * Endpoint to retrieve statistics about the cinema.
     *
     * @return ResponseEntity containing CinemaStatsResponse with cinema statistics.
     */
    @GetMapping("/stats")
    public ResponseEntity<CinemaStatsResponse> getStatsInfo() {
        CinemaStatsResponse cinemaStatsResponse = cinemaService.getStats();
        return ResponseEntity.ok(cinemaStatsResponse);
    }

    /**
     * Endpoint to purchase a cinema ticket.
     *
     * @param seat The seat to be purchased.
     * @return ResponseEntity containing PurchaseTicketResponse for the purchased seat.
     */
    @PutMapping("/purchase")
    public ResponseEntity<PurchaseTicketResponse> purchaseTicket(@RequestBody Seat seat) {
        PurchaseTicketResponse purchaseTicketResponse = cinemaService.purchaseSeat(seat);
        return ResponseEntity.ok(purchaseTicketResponse);
    }

    /**
     * Endpoint to return a cinema ticket.
     *
     * @param token TokenRequest containing the token associated with the purchased seat.
     * @return ResponseEntity containing ReturnedTicketResponse for the returned seat.
     */
    @PutMapping("/return")
    public ResponseEntity<ReturnedTicketResponse> returnTicket(@RequestBody TokenRequest token) {
        ReturnedTicketResponse returnResponse = cinemaService.returnTicket(token.token());
        return ResponseEntity.ok(returnResponse);
    }

    @PostMapping(path = "/register")
    public String register(@RequestBody RegistrationRequest request) {
        CinemaUser cinemaUser = new CinemaUser();
        cinemaUser.setUsername(request.username());
        cinemaUser.setPassword(passwordEncoder.encode(request.password()));
        cinemaUser.setAuthority(request.authority());

        userRepository.save(cinemaUser);

        return "New cinemaUser successfully registered";
    }
}
