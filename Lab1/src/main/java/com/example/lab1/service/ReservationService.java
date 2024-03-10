package com.example.lab1.service;

import com.example.lab1.dto.ReservationDTO;
import com.example.lab1.entity.Reservation;
import com.example.lab1.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository repository;

    public Reservation createTemporary(ReservationDTO reservationDTO) {
        Reservation reservation = Reservation.builder().
                hotelId(reservationDTO.getHotelId()).
                dateBegin(reservationDTO.getDateBegin()).
                dateEnd(reservationDTO.getDateEnd()).
                isPayed(false).
                build();
        return repository.save(reservation);
    }

    public void setTimer(Long reservationId){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                var reservation = repository.findById(reservationId);
                if (reservation.isPresent() && !reservation.get().isPayed()){
                    repository.deleteById(reservationId);
                }
            }
        };

        Timer timer = new Timer("Timer");
        long delay = 15 * 60 * 1000L;
        timer.schedule(task, delay);
    }

    public Reservation createCompleted(Long reservationId, Long customerId) {
        Optional<Reservation> optionalReservation = repository.findById(reservationId);
        if (optionalReservation.isEmpty()){
            throw new RuntimeException("Бронь с ID " + reservationId + " не найдена.");
        }

        Reservation reservation = optionalReservation.get();
        reservation.setPayed(true);
        reservation.setCustomerId(customerId);
        return repository.save(reservation);
    }
}
