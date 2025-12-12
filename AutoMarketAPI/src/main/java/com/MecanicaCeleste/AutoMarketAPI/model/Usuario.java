package com.MecanicaCeleste.AutoMarketAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode estar vazio.")
    private String nome;

    @Email(message = "Email inválido.")
    @NotBlank(message = "O email não pode estar vazio.")
    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @NotBlank(message = "A senha não pode estar vazia.")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    private String senha;

    @NotNull(message = "Selecione um papel.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Papel papel;
}