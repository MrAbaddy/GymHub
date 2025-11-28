package br.csi.gymhub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank; // Recomendado trocar NotNull por NotBlank para Strings
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank @Email
    private String login;

    @NotBlank
    private String senha;

    private String permissao;
}