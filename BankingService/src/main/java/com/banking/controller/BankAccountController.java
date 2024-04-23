package com.banking.controller;

import com.banking.dto.BankAccountDTO;
import com.banking.entity.BankAccount;
import com.banking.service.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@AllArgsConstructor
//@RequestMapping("")
public class BankAccountController {
    private BankAccountService bankAccountService;

    //@GetMapping()
    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public ResponseEntity<BankAccount> getAccountByCard(@RequestParam String cardNumber) {
        return new ResponseEntity<>(bankAccountService.findByCard(cardNumber), HttpStatus.OK);
    }

    //@PostMapping()
    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public ResponseEntity<BankAccount> createBankAccount(@RequestBody BankAccountDTO bankAccountDTO) {
        return new ResponseEntity<>(bankAccountService.create(bankAccountDTO), HttpStatus.OK);
    }
}
