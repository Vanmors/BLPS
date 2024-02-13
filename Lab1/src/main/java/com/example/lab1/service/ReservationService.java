package com.example.lab1.service;

import com.example.lab1.dto.ReservationDTO;
import com.example.lab1.entity.Reservation;
import com.example.lab1.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository repository;

    public Reservation create(ReservationDTO reservationDTO) {
        Reservation reservation = Reservation.builder().
                hotelId(reservationDTO.getHotelId()).
                dateBegin(reservationDTO.getDateBegin()).
                dateEnd(reservationDTO.getDateEnd()).
                build();
        return repository.save(reservation);
    }
}
