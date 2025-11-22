package com.nequiApp.nequiApp.utils;

import com.nequiApp.nequiApp.Entities.CuentaEntity;
import com.nequiApp.nequiApp.Exceptions.MontoInvalidException;
import com.nequiApp.nequiApp.Exceptions.SaldoInsuficiente;
import org.springframework.stereotype.Component;

@Component
public class CuentaValidator {

    public void validarSaldo(CuentaEntity cuenta, double monto) {
        if (monto <= 0) {
            throw new MontoInvalidException("Monto debe ser mayor a cero");
        }
        if(cuenta.getBalance() < monto){
             throw  new SaldoInsuficiente("Saldo insuficiente");

        }
    }

    //Actualizar saldos

    public  void   actualizarSaldo (CuentaEntity cuentaDestino , CuentaEntity cuentaOrigen , double monto){
           cuentaOrigen.setBalance(cuentaOrigen.getBalance() - monto);
           cuentaDestino.setBalance(cuentaDestino.getBalance() +  monto);
    }
}
