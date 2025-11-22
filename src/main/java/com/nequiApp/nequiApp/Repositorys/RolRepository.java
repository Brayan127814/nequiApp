package com.nequiApp.nequiApp.Repositorys;


import com.nequiApp.nequiApp.Entities.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<RolEntity, Long> {
     RolEntity findByRoleName(String roleName);
    Optional<RolEntity> findById(Long id);
}
