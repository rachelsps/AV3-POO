package com.MecanicaCeleste.AutoMarketAPI.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "veiculos")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String modelo;
    private Integer ano;
    private BigDecimal preco; // Alinhado com html
    private Integer estoque;  // Alinhado com html



    private Integer numeroPortas;
    private boolean temArCondicionado;
    private String cor;
    private String placa;

    @ManyToOne
    @JoinColumn(name = "cliente_id") // Nome da coluna no banco MySQL
    private Cliente cliente;
}