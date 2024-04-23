package com.banking.service;

import com.banking.dto.BankAccountDTO;
import com.banking.entity.BankAccount;
import com.banking.repository.BankAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BankAccountService {

    private final BankAccountRepository repository;


    public BankAccount create(BankAccountDTO bankAccountDTO) {
        BankAccount customer = BankAccount.builder().
                surname(bankAccountDTO.getSurname()).
                name(bankAccountDTO.getName()).balance(0).
                phoneNumber(bankAccountDTO.getPhoneNumber()).
                numberOfCard(bankAccountDTO.getNumberOfCard()).
                build();
        return repository.save(customer);
    }

    public List<BankAccount> findAll() {
        return repository.findAll();
    }

    public BankAccount update(BankAccount customer) {
        return repository.save(customer);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public BankAccount findByCard(String cardNumber) {
        return repository.findByNumberOfCard(cardNumber);
    }
}
