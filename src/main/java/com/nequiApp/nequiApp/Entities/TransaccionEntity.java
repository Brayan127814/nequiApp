package com.nequiApp.nequiApp.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.NonUniqueObjectException;

import java.time.LocalDateTime;

@Entity
@Table(name = "transacciones")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransaccionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "cuena_origen_id")
    private CuentaEntity cuentaOrigen;

    @ManyToOne
    @JoinColumn(name = "cuenta_destino_id")
    private CuentaEntity cuentaDestino;


    @Column(nullable = false)
    private String tipo; //RECARTGA , ENVIO, PAGO, RETIRO

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private String estado; //EXITOS, FALLIDA, PENDIENTE

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();


}
