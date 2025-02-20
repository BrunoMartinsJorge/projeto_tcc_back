package com.estudosJava.exercicios.controllers;

import com.estudosJava.exercicios.config.MapperModel;
import com.estudosJava.exercicios.model.dto.AlterarSenhaDTO;
import com.estudosJava.exercicios.model.dto.PerfilDTO;
import com.estudosJava.exercicios.model.entities.Perfil;
import com.estudosJava.exercicios.utilities.repositories.ContaRepositories;
import com.estudosJava.exercicios.utilities.services.ContaService;
import com.estudosJava.exercicios.utilities.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conta")
public class ContaController {

    @Autowired
    private ContaRepositories repositorioConta;

    @Autowired
    private ContaService contaService;

    private MapperModel mapear = new MapperModel();

    private TokenService token = new TokenService();

    @PostMapping("/cadastrar-conta")
    public ResponseEntity<?> adicionarUsuario(@RequestBody Perfil novaConta) {
        return contaService.criarNovaConta(novaConta);
    }

    @GetMapping("/logar-conta")
    public ResponseEntity<?> logarConta(@RequestParam String email, @RequestParam String senha) {
        return contaService.logarNaConta(email, senha);
    }

    @GetMapping
    public PerfilDTO buscarDados(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        String email = token.extrairDadosToken(authorizationHeader);
        return mapear.mapper().map(repositorioConta.findByEmail(email), PerfilDTO.class);
    }

    @PutMapping(value = "/alterar-dados")
    public ResponseEntity<?> alterarDadosPerfil(@RequestBody AlterarSenhaDTO perfil){
        return contaService.alterarDadosDaConta(perfil);
    }
}
