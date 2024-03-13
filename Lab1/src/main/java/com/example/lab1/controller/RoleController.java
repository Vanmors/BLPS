package com.example.lab1.controller;


import com.example.lab1.entity.Role;
import com.example.lab1.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleRepository repository;

    @PostMapping()
    public Role createRole(@RequestBody Role role) {
        return repository.save(role);
    }
}


