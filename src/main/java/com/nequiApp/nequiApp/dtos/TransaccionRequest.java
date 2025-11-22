package com.nequiApp.nequiApp.dtos;

import lombok.Data;

@Data
public class TransaccionRequest {

     //private  Long cuentaOrigenId;
     private  Long cuentaDestinoId;
     private String tipo;
     private  Double monto;
}
