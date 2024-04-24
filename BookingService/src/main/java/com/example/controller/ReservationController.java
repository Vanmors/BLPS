package com.example.controller;

import com.example.dto.CustomerDTO;
import com.example.dto.ReservationDTO;
import com.example.entity.Reservation;
import com.example.service.CustomerService;
import com.example.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    private final CustomerService customerService;

    @PostMapping("beginning")
    public ResponseEntity<Reservation> startToChooseHotelRoom(@RequestBody ReservationDTO reservationDTO) {
        var reservation = reservationService.createTemporary(reservationDTO);
        reservationService.setTimer(reservation.getId());
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    //todo добавить RESERVATION ПОЛЕ

    @PostMapping("confirmation")
    @Transactional
    public ResponseEntity<Reservation> completeToChooseHotelRoom(@RequestBody CustomerDTO customerDTO) {
        var customer = customerService.create(customerDTO);
        var ans = reservationService.createCompleted(customerDTO.getReservationId(), customer.getId(), customer.getNumberOfCard());
        return new ResponseEntity<>(ans, HttpStatus.OK);
    }

    //ТЕСТОВЫЙ КОНТРОЛЛЕР
    @PostMapping("test")
    @Transactional
    public ResponseEntity<Reservation> test(@RequestBody CustomerDTO customerDTO) {
        var customer = customerService.create(customerDTO);
        var ans = reservationService.createCompleted(customerDTO.getReservationId(), customer.getId(), customer.getNumberOfCard());
        return new ResponseEntity<>(ans, HttpStatus.OK);
    }
}
