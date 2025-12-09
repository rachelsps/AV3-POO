package com.MecanicaCeleste.AutoMarketAPI.dto.aluguel;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DadosAluguel {

    private Long id;

    @NotNull(message = "ID do veículo é obrigatório")
    private Long veiculoId;

    @NotNull(message = "ID do cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "Data de início é obrigatória")
    @FutureOrPresent
    private LocalDate dataInicio;

    @NotNull(message = "Data de devolução prevista é obrigatória")
    private LocalDate dataDevolucaoPrevista;

    @NotNull(message = "Valor da diária é obrigatório")
    private BigDecimal valorDiaria;
}