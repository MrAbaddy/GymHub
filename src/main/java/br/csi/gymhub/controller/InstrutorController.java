package br.csi.gymhub.controller;

import br.csi.gymhub.dto.InstrutorDTO;
import br.csi.gymhub.service.InstrutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instrutores")
@Tag(name = "Instrutor", description = "Gerenciamento dos instrutores da academia")
public class InstrutorController {

    private final InstrutorService instrutorService;

    public InstrutorController(InstrutorService instrutorService) {
        this.instrutorService = instrutorService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os instrutores")
    public ResponseEntity<List<InstrutorDTO>> listar() {
        try {
            return ResponseEntity.ok(instrutorService.listar());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um instrutor pelo ID")
    public ResponseEntity<InstrutorDTO> buscarPorId(@PathVariable Long id) {
        InstrutorDTO dto = instrutorService.buscarPorId(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Cria um novo instrutor")
    public ResponseEntity<InstrutorDTO> criar(@Valid @RequestBody InstrutorDTO dto) {
        try {
            InstrutorDTO criado = instrutorService.criar(dto);
            return ResponseEntity.ok(criado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um instrutor existente")
    public ResponseEntity<InstrutorDTO> atualizar(@PathVariable Long id, @Valid @RequestBody InstrutorDTO dto) {
        InstrutorDTO atualizado = instrutorService.atualizar(id, dto);
        if (atualizado != null) {
            return ResponseEntity.ok(atualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um instrutor pelo ID")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (instrutorService.deletar(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
