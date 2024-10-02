package com.pieropan.propostaapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_proposta")
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor")
    private Double valorSolicitado;
    private int prazoPagamento;

    private Boolean aprovada;
    private boolean integrada;
    private String observacao;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;


}
