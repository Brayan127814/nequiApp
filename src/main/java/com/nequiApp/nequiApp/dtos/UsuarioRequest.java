package com.nequiApp.nequiApp.dtos;

import com.nequiApp.nequiApp.Entities.RolEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {
    private String fullName;
    private String email;
    private String phone;
    private String password;

}
