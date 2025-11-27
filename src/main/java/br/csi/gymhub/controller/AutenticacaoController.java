package br.csi.gymhub.controller;

import br.csi.gymhub.model.DadosAutenticacao;
import br.csi.gymhub.service.TokenServiceJWT;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class AutenticacaoController {
    private final AuthenticationManager manager;
    private final TokenServiceJWT tokenService;

    @PostMapping
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
}
