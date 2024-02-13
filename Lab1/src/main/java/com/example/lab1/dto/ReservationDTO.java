package com.example.lab1.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationDTO {

    private Long hotelId;
    private LocalDate dateBegin;
    private LocalDate dateEnd;
}
