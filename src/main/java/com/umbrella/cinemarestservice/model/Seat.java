package com.umbrella.cinemarestservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SEATS")
@Getter
@NoArgsConstructor
public class Seat {

    @Id
    @Column(name = "seat_id")
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "seat_row")
    private int row;

    @Column(name = "seat_column")
    private int column;

    @Column(name = "seat_price")
    private int price;

    @Setter
    @JsonIgnore
    @Column(name = "seat_uuid")
    private String UUID;

    @Setter
    @JsonIgnore
    @Column(name = "seat_available")
    private boolean seatAvailable = true;

    public Seat(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
    }
}
