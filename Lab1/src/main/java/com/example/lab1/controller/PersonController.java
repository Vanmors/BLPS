package com.example.lab1.controller;

import com.example.lab1.dto.PersonDTO;
import com.example.lab1.entity.Person;
import com.example.lab1.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping
    public ResponseEntity<Person> create(@RequestBody PersonDTO personDTO) {
        return new ResponseEntity<>(personService.create(personDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Person>> readAll() {
        return new ResponseEntity<>(personService.readAll(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Person> update(@RequestBody Person person) {
        return new ResponseEntity<>(personService.update(person), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        personService.delete(id);
        return HttpStatus.OK;
    }
}
