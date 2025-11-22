package com.nequiApp.nequiApp.controller;

import com.nequiApp.nequiApp.Services.CuentaService;
import com.nequiApp.nequiApp.dtos.CuentaResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaControllers {
    private final CuentaService cuentaService;

    public CuentaControllers(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    //Consultar saldo

    @GetMapping("/saldo")
    public ResponseEntity<CuentaResponseDto> saldo() {
        CuentaResponseDto saldo = cuentaService.consultarSaldo();
        return ResponseEntity.ok(saldo);
    }
}
