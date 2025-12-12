package com.MecanicaCeleste.AutoMarketAPI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank(message = "O nome não pode estar vazio.")
    @Pattern(
        regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$",
        message = "O nome deve conter apenas letras e espaços."
    )
    private String nome;

    @NotBlank(message = "O CPF não pode estar vazio.")
    @Size(min = 11, max = 11, message = "O CPF deve conter exatamente 11 dígitos.")
    @Pattern(regexp = "^[0-9]{11}$", message = "O CPF deve conter apenas números.")
    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    @NotBlank(message = "O telefone não pode estar vazio.")
    @Size(min = 8, max = 13, message = "O telefone deve conter entre 8 e 13 dígitos.")
    @Pattern(regexp = "^[0-9]{8,13}$", message = "O telefone deve conter apenas números.")
    @Column(length = 13)
    private String telefone;

      @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Veiculo> veiculos = new ArrayList<>();
}