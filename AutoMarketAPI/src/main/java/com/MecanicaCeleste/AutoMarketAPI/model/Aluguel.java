package com.MecanicaCeleste.AutoMarketAPI.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Aluguel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private LocalDate dataInicio;
    private LocalDate dataFimPrevista;
    private LocalDate dataDevolucaoEfetiva;

    private BigDecimal valorDiaria;
    private BigDecimal valorTotal; // Calculado no fecho do contrato

    private boolean ativo = true; // Define se o aluguer está em curso
}