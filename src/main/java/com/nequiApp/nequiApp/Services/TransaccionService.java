package com.nequiApp.nequiApp.Services;

import com.nequiApp.nequiApp.Entities.CuentaEntity;
import com.nequiApp.nequiApp.Entities.TransaccionEntity;
import com.nequiApp.nequiApp.Repositorys.CuentaRepository;
import com.nequiApp.nequiApp.Repositorys.TransaccionRepository;
import com.nequiApp.nequiApp.dtos.TransaccionRequest;
import com.nequiApp.nequiApp.dtos.TransaccionResponse;
import com.nequiApp.nequiApp.utils.CuentaValidator;
import jakarta.persistence.ManyToOne;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransaccionService {

    private final CuentaRepository cuentaRepository;
    private final TransaccionRepository transaccionRepository;
    private final CuentaValidator cuentaValidator;


    public TransaccionService(CuentaRepository cuentaRepository, TransaccionRepository transaccionRepository, CuentaValidator cuentaValidator) {
        this.cuentaRepository = cuentaRepository;
        this.transaccionRepository = transaccionRepository;
        this.cuentaValidator = cuentaValidator;
    }

    @Transactional
    public TransaccionResponse enviarDinero(TransaccionRequest data) {


        //OBTENER EL USUARIO AUTENTICADO
        String email = SecurityContextHolder.getContext().getAuthentication().getName();


        //Buscar cuenta de origen

        CuentaEntity cuentaOrigen = cuentaRepository.findByUsuario_Email(email)
                .orElseThrow(() -> new RuntimeException("Cuenta de origen no encontrada"));
        //Buscar cuenta de destion
        CuentaEntity cuentaDestino = cuentaRepository.findById(data.getCuentaDestinoId())
                .orElseThrow(() -> new RuntimeException("Cuenta de destino no encontrada"));


        //   validar saldo
        cuentaValidator.validarSaldo(cuentaOrigen, data.getMonto());

        //actualizar saldo

        cuentaValidator.actualizarSaldo(cuentaDestino, cuentaOrigen, data.getMonto());


        //Crear transacccion
        TransaccionEntity transaccion = new TransaccionEntity();
        transaccion.setCuentaOrigen(cuentaOrigen);
        transaccion.setCuentaDestino(cuentaDestino);
        transaccion.setTipo(data.getTipo());
        transaccion.setMonto(data.getMonto());
        transaccion.setEstado("EXITOSA");
        transaccion.setFecha(LocalDateTime.now());


        TransaccionEntity transacccionGuardada = transaccionRepository.save(transaccion);

        return new TransaccionResponse(transacccionGuardada.getId(), transaccion.getCuentaOrigen().getAccountNumber(), transaccion.getCuentaDestino().getAccountNumber(), transaccion.getMonto(), transaccion.getTipo(), transaccion.getEstado(), transaccion.getFecha());
    }


    //HISTORIAL DE TRANSACCIONES
    public List<TransaccionResponse> hisotorial(){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TransaccionEntity> historial = transaccionRepository.findByCuentaOrigen_Usuario_EmailOrCuentaDestino_Usuario_EmailOrderByFechaDesc(email,email);

        if(historial.isEmpty()){
             throw  new RuntimeException("No cuentas con historial de transacciones");
        }

        return  historial.stream().map(t -> new TransaccionResponse(t.getId(),t.getCuentaOrigen().getAccountNumber(), t.getCuentaDestino().getAccountNumber(),t.getMonto(),t.getTipo(),t.getEstado(),t.getFecha())).toList();
    }

}
