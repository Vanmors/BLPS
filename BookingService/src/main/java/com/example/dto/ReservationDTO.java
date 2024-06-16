package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ReservationDTO {

    private Long hotelId;
    private LocalDate dateBegin;
    private LocalDate dateEnd;
}
