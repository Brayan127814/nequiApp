package com.nequiApp.nequiApp.Repositorys;

import com.nequiApp.nequiApp.Entities.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<CuentaEntity, Long> {
    boolean existsByAccountNumber(String accountNumer);
    Optional<CuentaEntity> findById(Long id);
    Optional<CuentaEntity> findByUsuario_Email(String email);


}
