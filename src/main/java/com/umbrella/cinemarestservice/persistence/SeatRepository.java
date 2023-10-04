package com.umbrella.cinemarestservice.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import com.umbrella.cinemarestservice.model.Seat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on Seat entities.
 *
 * This repository extends Spring Data JPA's JpaRepository, which provides
 * built-in methods for common database operations such as save, delete, and find.
 * Additionally, custom query methods are defined here for specific seat-related queries.
 *
 * @author KrigRaseri (pen name)
 * @version 1.0
 */
@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    /**
     * Retrieve a seat by its row and column numbers.
     *
     * @param row    The row number of the seat.
     * @param column The column number of the seat.
     * @return An optional containing the seat if found, or empty if not found.
     */
    @Query(value = "SELECT s FROM Seat s WHERE s.row = ?1 AND s.column = ?2")
    Optional<Seat> findByRowAndColumn(int row, int column);

    /**
     * Retrieve a seat by its unique identifier (UUID).
     *
     * @param UUID The unique identifier of the seat.
     * @return An optional containing the seat if found, or empty if not found.
     */
    @Query(value = "SELECT s FROM Seat s WHERE s.UUID = ?1")
    Optional<Seat> findByUUID(String UUID);
}
