package com.example.lab1.controller;


import com.example.lab1.dto.CustomerDTO;
import com.example.lab1.entity.Customer;
import com.example.lab1.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@AllArgsConstructor
@RequestMapping("/card")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping()
    public ResponseEntity<Customer> create(@RequestBody CustomerDTO customerDTO) {
        Customer customer;
        try {
            customer = customerService.create(customerDTO);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Customer>> readAll() {
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Customer> update(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.update(customer), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        customerService.delete(id);
        return HttpStatus.OK;
    }
}
