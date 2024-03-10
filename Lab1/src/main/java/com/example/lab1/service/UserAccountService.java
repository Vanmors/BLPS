package com.example.lab1.service;

import com.example.lab1.dto.UserAccountDTO;
import com.example.lab1.entity.UserAccount;
import com.example.lab1.repository.UserAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserAccountService {

    private final UserAccountRepository repository;


    public UserAccount create(UserAccountDTO userAccountDTO){
        UserAccount person = UserAccount.builder().
                username(userAccountDTO.getUsername()).
                password(userAccountDTO.getPassword()).
                role(userAccountDTO.getRole()).
                build();
        return repository.save(person);
    }

    public List<UserAccount> readAll() {
        return repository.findAll();
    }

    public UserAccount update(UserAccount person) {
        return repository.save(person);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
