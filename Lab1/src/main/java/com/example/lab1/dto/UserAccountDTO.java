package com.example.lab1.dto;

import com.example.lab1.entity.Role;
import lombok.Data;

@Data
public class UserAccountDTO {

    private String username;
    private String password;
    private Role role;
}
