package com.MecanicaCeleste.AutoMarketAPI.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode estar vazio.") //impede de ser vazio
    private String nome;

    @NotBlank(message = "O CPF não pode estar vazio.") //impede de ser vazio
    @Column(unique = true, nullable = false, length = 11) // força a ser um valor unico e não pode ser valor nulo, com um tamanho obrigatorio de 11
    private String cpf;

    @Size(min = 11, max = 11, message = "O telefone deve conter exatamente 11 dígitos (ex: 71987654321)") // força o tamanho a ser 11
    private String telefone;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL) //relacionamento one to many pro veiculo
    private List<Veiculo> veiculos = new ArrayList<>();
}
