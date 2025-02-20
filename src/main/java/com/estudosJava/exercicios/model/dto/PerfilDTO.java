package com.estudosJava.exercicios.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PerfilDTO {

    @JsonIgnore
    private int id;
    private String nome;
    private String email;
    @JsonIgnore
    private String senha;
}
