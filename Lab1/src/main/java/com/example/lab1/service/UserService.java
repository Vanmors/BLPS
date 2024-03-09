package com.example.lab1.service;

import com.example.lab1.dto.UserDTO;
import com.example.lab1.entity.User;
import com.example.lab1.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User create(UserDTO userDTO){
        User person = User.builder().
                email(userDTO.getEmail()).
                password(userDTO.getPassword()).
                build();
        return repository.save(person);
    }

    public List<User> readAll() {
        return repository.findAll();
    }

    public User update(User person) {
        return repository.save(person);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
