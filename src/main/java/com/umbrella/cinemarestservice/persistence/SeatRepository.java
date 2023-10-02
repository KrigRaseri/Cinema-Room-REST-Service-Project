package com.umbrella.cinemarestservice.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import com.umbrella.cinemarestservice.model.Seat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Query(value = "SELECT s FROM Seat s WHERE s.row = ?1 AND s.column = ?2")
    Optional<Seat> findByRowAndColumn(int row, int column);

    @Query(value = "SELECT s FROM Seat s WHERE s.UUID = ?1")
    Optional<Seat> findByUUID(String UUID);
}
