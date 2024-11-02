package com.lemes.propostaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropostaResponseDto {

    private Long id;
    private String nome;
    private String sobrenome;
    private String telefone;
    private String cpf;
    private Double renda;
    private String valorSolicitadoFmt;
    private int prazoPagamento;
    private Boolean aprovada;
    private String observacao;
}
