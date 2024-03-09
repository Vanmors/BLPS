package com.example.lab1.controller;

import com.example.lab1.dto.UserAccountDTO;
import com.example.lab1.entity.UserAccount;
import com.example.lab1.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserAccount> create(@RequestBody UserAccountDTO userAccountDTO) {
        return new ResponseEntity<>(userService.create(userAccountDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserAccount>> readAll() {
        return new ResponseEntity<>(userService.readAll(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserAccount> update(@RequestBody UserAccount userAccount) {
        return new ResponseEntity<>(userService.update(userAccount), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        userService.delete(id);
        return HttpStatus.OK;
    }
}
