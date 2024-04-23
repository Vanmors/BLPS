package com.banking.controller;

import com.banking.dto.PaymentProcedureDTO;
import com.banking.entity.BankAccount;
import com.banking.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@AllArgsConstructor
@RequestMapping("/money-operation")
public class PaymentController {
    private PaymentService paymentService;

    //хочю заплатить за что-то
    @PostMapping("pay")
    public ResponseEntity<BankAccount> pay(@RequestBody PaymentProcedureDTO paymentProcedureDTO) {
        return new ResponseEntity<>(paymentService.pay(paymentProcedureDTO), HttpStatus.OK);
    }

    //хочю пополнить счет
    @PostMapping("replenish")
    public ResponseEntity<BankAccount> replenishBalance(@RequestBody PaymentProcedureDTO paymentProcedureDTO) {
        return new ResponseEntity<>(paymentService.replenish(paymentProcedureDTO), HttpStatus.OK);
    }
}
