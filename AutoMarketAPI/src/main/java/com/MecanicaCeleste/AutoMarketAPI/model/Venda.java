package com.MecanicaCeleste.AutoMarketAPI.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Veiculo veiculo;

    @ManyToOne
    @JoinColumn(name = "vendedor_id", referencedColumnName = "id")
    private Usuario vendedor;

    private BigDecimal precoVenda;
    private LocalDateTime dataVenda = LocalDateTime.now();
}