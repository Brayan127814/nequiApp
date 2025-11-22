package com.nequiApp.nequiApp.controller;

import com.nequiApp.nequiApp.Entities.TransaccionEntity;
import com.nequiApp.nequiApp.Services.TransaccionService;
import com.nequiApp.nequiApp.dtos.TransaccionRequest;
import com.nequiApp.nequiApp.dtos.TransaccionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {
    private final TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @PostMapping("/enviar")
    public ResponseEntity<TransaccionResponse> enviarDinero(@RequestBody TransaccionRequest request) {
        TransaccionResponse response = transaccionService.enviarDinero(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    //consultar historial
    @GetMapping("/historial")
    public ResponseEntity<List<TransaccionResponse>> allHistoy() {
        List<TransaccionResponse> historial = transaccionService.hisotorial();
        return ResponseEntity.ok(historial);
    }
}