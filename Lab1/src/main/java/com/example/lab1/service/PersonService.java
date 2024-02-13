package com.example.lab1.service;

import com.example.lab1.dto.PersonDTO;
import com.example.lab1.entity.Person;
import com.example.lab1.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository repository;

    public Person create(PersonDTO personDTO){
        Person person = Person.builder().
                name(personDTO.getName()).
                surname(personDTO.getSurname()).
                email(personDTO.getEmail()).
                phoneNumber(personDTO.getPhoneNumber()).
                nation(personDTO.getNation()).
                build();
        return repository.save(person);
    }

    public List<Person> readAll() {
        return repository.findAll();
    }

    public Person update(Person person) {
        return repository.save(person);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
