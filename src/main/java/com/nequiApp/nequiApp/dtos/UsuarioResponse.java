package com.nequiApp.nequiApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String accountNumber;
    private List<String> roles;
}
