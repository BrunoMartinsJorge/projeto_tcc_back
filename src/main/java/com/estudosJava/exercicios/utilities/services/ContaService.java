package com.estudosJava.exercicios.utilities.services;

import com.estudosJava.exercicios.config.MapperModel;
import com.estudosJava.exercicios.model.dto.AlterarSenhaDTO;
import com.estudosJava.exercicios.model.dto.PerfilDTO;
import com.estudosJava.exercicios.model.entities.Perfil;
import com.estudosJava.exercicios.utilities.exceptions.CadastroException;
import com.estudosJava.exercicios.utilities.repositories.ContaRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContaService {

    @Autowired
    private ContaRepositories repositories;

    @Autowired
    private TokenService token;

    private MapperModel mapear = new MapperModel();

    public ResponseEntity<?> criarNovaConta(Perfil perfil) {
        if (!repositories.existsByEmail(perfil.getEmail())) {
            repositories.save(perfil);
            return ResponseEntity.ok().body("{\"token\": \"" + token.criarToken(perfil) + "\"}");
        } else {
            throw CadastroException.EmailJaCdastrado(perfil.getEmail());
        }
    }

    public ResponseEntity<?> logarNaConta(String email, String senha) {
        Perfil dadosDaConta = repositories.findByEmail(email);
        if (dadosDaConta != null) {
            if (senha.equals(dadosDaConta.getSenha())) {
                return ResponseEntity.ok().body("{\"token\": \"" + token.criarToken(dadosDaConta) + "\"}");
            } else {
                throw CadastroException.SenhaInvalida();
            }
        } else {
            throw CadastroException.ContaNaoCadastrada(email);
        }
    }

    public ResponseEntity<?> alterarDadosDaConta(AlterarSenhaDTO perfil) {
        Perfil dadosDaConta = repositories.findByEmail(perfil.getEmail());
        if (dadosDaConta != null) {
            dadosDaConta.setSenha(perfil.getSenhaNova());
            repositories.save(dadosDaConta);
        } else {
            throw CadastroException.ContaNaoCadastrada(perfil.getEmail());
        }
        return ResponseEntity.ok().body(mapear.mapper().map(dadosDaConta, PerfilDTO.class));
    }
}
