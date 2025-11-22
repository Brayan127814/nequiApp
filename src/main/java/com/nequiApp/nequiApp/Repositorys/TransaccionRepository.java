package com.nequiApp.nequiApp.Repositorys;

import com.nequiApp.nequiApp.Entities.TransaccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransaccionRepository extends JpaRepository<TransaccionEntity, Long> {

    //LISTAR CUENTAS DE ORIGEN
    List<TransaccionEntity> findByCuentaOrigenIdOrderByFechaDesc(Long cuentaOrigen);

    //LISTAR CUENTAS DE DESTINO
    List<TransaccionEntity> findByCuentaDestinoIdOrderByFechaDesc(Long cuentaDestino);

    List<TransaccionEntity> findByCuentaOrigen_Usuario_EmailOrCuentaDestino_Usuario_EmailOrderByFechaDesc(
            String emailOrigen, String emailDestino);


}
