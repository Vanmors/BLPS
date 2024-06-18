package com.example.service;

import com.example.dto.PaymentDTO;
import com.example.dto.ReservationDTO;
import com.example.entity.HotelNumber;
import com.example.entity.Reservation;
import com.example.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

@Service
@AllArgsConstructor
public class ReservationService {

    private final String bankingApiUrl = "http://localhost:8082/money-operation/pay";

    private final ReservationRepository repository;
    private final HotelNumberService hotelNumberService;

    public Reservation createTemporary(ReservationDTO reservationDTO) {
        Reservation reservation = Reservation.builder().
                hotelId(reservationDTO.getHotelId()).
                dateBegin(reservationDTO.getDateBegin()).
                dateEnd(reservationDTO.getDateEnd()).
                isPayed(false).
                build();
        return repository.save(reservation);
    }

    public void setTimer(Long reservationId) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                var reservation = repository.findById(reservationId);
                if (reservation.isPresent() && !reservation.get().isPayed()) {
                    repository.deleteById(reservationId);
                }
            }
        };

        Timer timer = new Timer("Timer");
        long delay = 60L * 1000L * 15L; //*15L
        timer.schedule(task, delay);
    }

    public Reservation createCompleted(Long reservationId, Long customerId, String numberOfCard) {
        Optional<Reservation> optionalReservation = repository.findById(reservationId);
        if (optionalReservation.isEmpty()) {
            throw new RuntimeException("Бронь с ID " + reservationId + " не найдена.");
        }

        Reservation reservation = optionalReservation.get();
        Optional<HotelNumber> hotelNumberOptional = hotelNumberService.findById(reservation.getHotelId());
        if (hotelNumberOptional.isEmpty()) {
            throw new RuntimeException("Отеля с таким ID" + reservation.getHotelId() + " не существует.");
        }

        HotelNumber hotelNumber = hotelNumberOptional.get();

        //query
        //===============================
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var paymentDTO = new PaymentDTO(numberOfCard, "pay", hotelNumber.getPayment());
        HttpEntity<PaymentDTO> requestEntity = new HttpEntity<>(paymentDTO, headers);
        ResponseEntity<Void> response = restTemplate.postForEntity(bankingApiUrl, requestEntity, Void.class);

        if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            throw new RuntimeException("Недостаточно средств на карте" + numberOfCard);
        }

        ///==========================
        reservation.setPayed(true);
        reservation.setCustomerId(customerId);
        return repository.save(reservation);
    }

    public Reservation createCompletedForCamunda(Long reservationId, Long customerId, String numberOfCard) {
        Optional<Reservation> optionalReservation = repository.findById(reservationId);
        if (optionalReservation.isEmpty()) {
            throw new BpmnError("BookingIdNotExists","Бронь с ID " + reservationId + " не найдена.");
        }

        Reservation reservation = optionalReservation.get();
        Optional<HotelNumber> hotelNumberOptional = hotelNumberService.findById(reservation.getHotelId());
        if (hotelNumberOptional.isEmpty()) {
            throw new BpmnError("HotelIdNotExists","Отеля с таким ID" + reservation.getHotelId() + " не существует.");
        }

        HotelNumber hotelNumber = hotelNumberOptional.get();

        //query
        //===============================
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var paymentDTO = new PaymentDTO(numberOfCard, "pay", hotelNumber.getPayment());
        HttpEntity<PaymentDTO> requestEntity = new HttpEntity<>(paymentDTO, headers);
        ResponseEntity<Void> response = restTemplate.postForEntity(bankingApiUrl, requestEntity, Void.class);

        if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            throw new BpmnError("NotEnoughMoney","Недостаточно средств на карте" + numberOfCard);
        }

        ///==========================
        reservation.setPayed(true);
        reservation.setCustomerId(customerId);
        return repository.save(reservation);
    }
}
