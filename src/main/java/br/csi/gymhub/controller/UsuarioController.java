package br.csi.gymhub.controller;

import br.csi.gymhub.model.DadosUsuario;
import br.csi.gymhub.model.Usuario;
import br.csi.gymhub.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
public class UsuarioController {
    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<DadosUsuario> criar(
            @RequestBody @Valid Usuario usuario,
            UriComponentsBuilder uriBuilder
    ) {
        DadosUsuario usuarioCriado = service.cadastrar(usuario);
        URI uri = uriBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(usuarioCriado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosUsuario> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findUsuario(id));
    }

    @GetMapping
    public ResponseEntity<List<DadosUsuario>> findAll() {
        return ResponseEntity.ok(service.findAllUsuarios());
    }
}
