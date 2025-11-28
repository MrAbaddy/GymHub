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
@RequestMapping("/gymhub/usuario")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<DadosUsuario> criar(
            @RequestBody @Valid Usuario usuario,
            UriComponentsBuilder uriBuilder
    ) {
        DadosUsuario usuarioCriado = service.cadastrar(usuario);
        URI uri = uriBuilder.path("/gymhub/usuario/{id}").buildAndExpand(usuarioCriado.id()).toUri();
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

    @PutMapping("/{id}")
    public ResponseEntity<DadosUsuario> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid Usuario usuario
    ) {
        DadosUsuario usuarioAtualizado = service.atualizar(id, usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}