package com.banking.controller;

import com.banking.dto.PaymentProcedureDTO;
import com.banking.entity.BankAccount;
import com.banking.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Обработчик исключений для RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        if (ex.getMessage().equals("Insufficient funds on balance")) {
            return new ResponseEntity<>("Ошибка: недостаточно средст на балансе этой карты", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Внутренняя ошибка сервера", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
