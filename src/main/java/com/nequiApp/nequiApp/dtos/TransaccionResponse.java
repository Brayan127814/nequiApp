package com.nequiApp.nequiApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransaccionResponse {

    private Long id;
    private String cuentaOrigen;
    private String cuentaDestino;
    private Double monto;
    private String tipo;      // ENVIO, RECARGA, PAGO, RETIRO
    private String estado;    // EXITOSA, FALLIDA, PENDIENTE
    private LocalDateTime fecha;
}
