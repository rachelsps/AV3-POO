package com.MecanicaCeleste.AutoMarketAPI.dto.venda;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DadosVenda {
    private Long idVenda;
    private String nomeCliente;
    private String modeloVeiculo;
    private BigDecimal precoVendido;
    private LocalDateTime data;
}