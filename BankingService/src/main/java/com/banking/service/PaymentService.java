package com.banking.service;

import com.banking.dto.BankAccountDTO;
import com.banking.dto.PaymentProcedureDTO;
import com.banking.entity.BankAccount;
import com.banking.entity.BankTransaction;
import com.banking.repository.BankAccountRepository;
import com.banking.repository.BankTransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PaymentService {
    private BankAccountService bankAccountService;

    private final BankTransactionRepository repository;

    @Transactional
    public BankAccount replenish(PaymentProcedureDTO paymentProcedureDTO) {
        paymentProcedureDTO.setStatus("replenish");

        BankAccount bankAccount = bankAccountService.findByCard(paymentProcedureDTO.getNumberOfCard());
        if (bankAccount == null) {
            throw new RuntimeException("There's no bankAccount by this cardNumber");
        }
        bankAccount.setBalance(bankAccount.getBalance() + paymentProcedureDTO.getAmount());

        repository.save(createBankingTransaction(paymentProcedureDTO, bankAccount));

        return bankAccount;
    }

    @Transactional
    public BankAccount pay(PaymentProcedureDTO paymentProcedureDTO) {
        paymentProcedureDTO.setStatus("pay");

        BankAccount bankAccount = bankAccountService.findByCard(paymentProcedureDTO.getNumberOfCard());
        if (bankAccount == null) {
            throw new RuntimeException("There's no bankAccount by this cardNumber");
        }

        if (bankAccount.getBalance() < paymentProcedureDTO.getAmount()) {
            throw new RuntimeException("Insufficient funds on balance");
        }
        bankAccount.setBalance(bankAccount.getBalance() - paymentProcedureDTO.getAmount());

        repository.save(createBankingTransaction(paymentProcedureDTO, bankAccount));

        return bankAccount;
    }

    private BankTransaction createBankingTransaction(PaymentProcedureDTO paymentProcedureDTO, BankAccount bankAccount) {
        BankTransaction bankTransaction = BankTransaction.builder().
                surname(bankAccount.getSurname()).
                name(bankAccount.getName()).
                amount(paymentProcedureDTO.getAmount()).
                status(paymentProcedureDTO.getStatus()).
                build();
        return repository.save(bankTransaction);
    }


}
