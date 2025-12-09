package com.MecanicaCeleste.AutoMarketAPI.dto.veiculo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class DadosVeiculo {

    private Long id; // Necessário para edição

    @NotBlank(message = "A marca não pode estar vazia.")
    private String marca;

    @NotBlank(message = "O modelo não pode estar vazio.")
    private String modelo;

    @NotNull(message = "O ano não pode estar vazio.")
    private Integer ano;

    @NotBlank(message = "A categoria não pode estar vazia.") //impede de ser vazio
    private String categoria;

    @NotBlank(message = "A placa não pode estar vazia")
    private String placa;

    private Integer numeroPortas;
    private boolean temArCondicionado;

    // Campos de valores não são obrigatórios pois o carro pode ser
    // cadastrado só para venda, só para aluguel ou ambos.
    private BigDecimal valorVenda;
    private BigDecimal valorDiaria;
}