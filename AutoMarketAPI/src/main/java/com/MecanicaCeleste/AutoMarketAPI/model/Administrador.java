package com.MecanicaCeleste.AutoMarketAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Administrador
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Email inválido.") //checa se o formato do email é valido
    @Column(unique = true, nullable = false) // força a ser valor unico e não pode ser valor nulo
    @NotBlank(message = "O email não pode estar vazio.") //impede de ser vazio
    private String email;

    @JsonIgnore //não permite a senha aparecer no log do json
    @NotBlank(message = "Senha não pode estar vazia.") //impede de ser vazio
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres") //tamanho minimo da senha é 6
    private String senha;
}
