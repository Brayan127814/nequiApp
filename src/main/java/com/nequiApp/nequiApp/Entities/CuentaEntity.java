package com.nequiApp.nequiApp.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cuentas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true )
public class CuentaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private double balance = 0.0;
    @Column(nullable = false)
    private String stado = "ACTIVE";
    //Relacion con usuario

    @ManyToOne
    @JoinColumn(name = "ususrio_id", nullable = false)
    private UsuarioEntity usuario;


    //Relaccion con transacciones cono origen
    @OneToMany(mappedBy = "cuentaOrigen", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TransaccionEntity> transaccionesOrigen = new HashSet<>();

    // Relación con transacciones como destino
    @OneToMany(mappedBy = "cuentaDestino", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TransaccionEntity> transaccionesDestino = new HashSet<>();
}
