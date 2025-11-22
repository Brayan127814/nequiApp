package com.nequiApp.nequiApp.Services;

import com.nequiApp.nequiApp.Entities.CuentaEntity;
import com.nequiApp.nequiApp.Exceptions.CuentaNotFountExceptions;
import com.nequiApp.nequiApp.Repositorys.CuentaRepository;
import com.nequiApp.nequiApp.dtos.CuentaResponseDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    private CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    //Consultar saldo
    public CuentaResponseDto consultarSaldo (){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CuentaEntity saldo = cuentaRepository.findByUsuario_Email(email).orElseThrow(()-> new CuentaNotFountExceptions("Cuenta no encontrada")) ;
        return  new CuentaResponseDto(saldo.getUsuario().getFullName(), saldo.getAccountNumber(), saldo.getBalance());
    }
}
