package com.example.dto;

import com.example.entity.Role;
import lombok.Data;

@Data
public class UserAccountDTO {

    private String username;
    private String password;
    private Role role;
}
