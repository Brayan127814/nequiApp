package com.nequiApp.nequiApp.dtos;

import com.nequiApp.nequiApp.Entities.UsuarioEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuentaResponseDto {
    private String usuario;
    private String accountNumber;
    private double balance;
}
