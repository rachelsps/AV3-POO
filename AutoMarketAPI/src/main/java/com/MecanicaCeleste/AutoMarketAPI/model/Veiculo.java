package com.MecanicaCeleste.AutoMarketAPI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O modelo não pode estar vazio.") //impede de ser vazio
    private String modelo;

    @NotBlank(message = "A marca não pode estar vazia.") //impede de ser vazio
    private String marca;

    @NotBlank(message = "O ano não pode estar vazio.") //impede de ser vazio
    private int ano;

    @NotBlank(message = "A categoria não pode estar vazia.") //impede de ser vazio
    private String categoria;

    @ManyToOne //relação many to one com cliente
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
