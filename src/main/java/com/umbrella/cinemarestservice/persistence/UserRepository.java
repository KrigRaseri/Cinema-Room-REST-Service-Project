package com.umbrella.cinemarestservice.persistence;

import com.umbrella.cinemarestservice.model.CinemaUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<CinemaUser, Integer> {
    Optional<CinemaUser> findAppUserByUsername(String username);
}
