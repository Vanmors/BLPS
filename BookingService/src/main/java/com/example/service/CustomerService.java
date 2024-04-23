package com.example.service;

import com.example.dto.CustomerDTO;
import com.example.entity.Customer;
import com.example.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;


    public Customer create(CustomerDTO customerDTO) {
        Customer customer = Customer.builder().
                surname(customerDTO.getSurname()).
                name(customerDTO.getName()).
                email(customerDTO.getEmail()).
                phoneNumber(customerDTO.getPhoneNumber()).
                nation(customerDTO.getNation()).
                numberOfCard(customerDTO.getNumberOfCard()).
                build();
        return repository.save(customer);
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public Customer update(Customer customer) {
        return repository.save(customer);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
