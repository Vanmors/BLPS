package com.example.controller;

import com.example.dto.CustomerDTO;
import com.example.dto.ReservationDTO;
import com.example.entity.Reservation;
import com.example.service.CustomerService;
import com.example.service.ReservationService;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@RestController
@AllArgsConstructor
@RequestMapping("/reservation")
public class PreliminaryReservationController implements JavaDelegate {
    private final ReservationService reservationService;
    private final CustomerService customerService;

    @PostMapping("beginning")
    public ResponseEntity<Reservation> startToChooseHotelRoom(@RequestBody ReservationDTO reservationDTO) {
        var reservation = reservationService.createTemporary(reservationDTO);
        reservationService.setTimer(reservation.getId());
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }
    /*
    @PostMapping("confirmation")
    @Transactional
    public ResponseEntity<Reservation> completeToChooseHotelRoom(@RequestBody CustomerDTO customerDTO) {
        var customer = customerService.create(customerDTO);
        var ans = reservationService.createCompleted(customerDTO.getReservationId(), customer.getId(), customer.getNumberOfCard());
        return new ResponseEntity<>(ans, HttpStatus.OK);
    }
     */

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long hotelId = (Long) delegateExecution.getVariable("hotelId");
        LocalDate dataBegin = convertToLocalDate((Date) delegateExecution.getVariable("dataBegin"));
        LocalDate dataEnd =  convertToLocalDate((Date) delegateExecution.getVariable("dataEnd"));

        ReservationDTO reservationDTO = new ReservationDTO(hotelId, dataBegin, dataEnd);

        var reservation = reservationService.createTemporary(reservationDTO);
        reservationService.setTimer(reservation.getId());
        //System.out.println(reservation);
        //delegateExecution.setVariable("reservationResult", reservation);
        delegateExecution.setVariable("ok", true);
        delegateExecution.setVariable("reservationId", reservation.getId());
    }

    public static LocalDate convertToLocalDate(Date date) {
        Instant instant = date.toInstant();

        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
