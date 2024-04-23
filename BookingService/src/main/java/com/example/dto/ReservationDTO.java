package com.example.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationDTO {

    private Long hotelId;
    private LocalDate dateBegin;
    private LocalDate dateEnd;
}
