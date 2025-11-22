package com.nequiApp.nequiApp.Repositorys;

import com.nequiApp.nequiApp.Entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    UsuarioEntity findByEmail(String email);

    UsuarioEntity findByPhone (String phone);
}
