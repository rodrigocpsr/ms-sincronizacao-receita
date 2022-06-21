package br.com.desafio.sincronizacaoreceita.model;

import lombok.Data;

@Data
public class ContaDTO {

    private String agencia;
    private String conta;
    private String saldo;
    private String status;
    private boolean resultado;

}
