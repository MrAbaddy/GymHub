package br.csi.gymhub.controller;

import br.csi.gymhub.model.DadosAutenticacao;
import br.csi.gymhub.model.Usuario;
import br.csi.gymhub.service.TokenServiceJWT;
import br.csi.gymhub.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/gymhub")
@AllArgsConstructor
public class AutenticacaoController {

    private final AuthenticationManager manager;
    private final TokenServiceJWT tokenService;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody DadosAutenticacao dados) {
        try {
            Authentication autenticado = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
            Authentication at = manager.authenticate(autenticado);

            User user = (User) at.getPrincipal();
            String token = tokenService.gerarToken(user);

            return ResponseEntity.ok().body(Map.of("token", token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity registrar(@RequestBody Usuario usuario) {
        try {
            this.usuarioService.cadastrar(usuario);
            // MUDANÇA AQUI: Map.of cria um JSON { "mensagem": "..." }
            return ResponseEntity.status(201).body(Map.of("mensagem", "Usuário criado com sucesso!"));
        } catch (Exception e) {
            // Aqui também, para manter o padrão
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }}