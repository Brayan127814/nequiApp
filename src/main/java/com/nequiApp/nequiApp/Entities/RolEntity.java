package com.nequiApp.nequiApp.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.lang.Objects;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // ← ESTA LINEA SOLAMENTE
public class RolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include // ← Y ESTA
    private Long id;

    @Column(nullable = false, unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    @Builder.Default
    private Set<UsuarioEntity> usuarios = new HashSet<>();
}