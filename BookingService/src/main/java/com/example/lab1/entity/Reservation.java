package com.example.lab1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Reservation {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    private Long hotelId;
    private LocalDate dateBegin;
    private LocalDate dateEnd;

    private boolean isPayed;
    private Long customerId;
}