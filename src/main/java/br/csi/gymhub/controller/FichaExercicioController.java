package br.csi.gymhub.controller;

import br.csi.gymhub.dto.FichaExercicioDTO;
import br.csi.gymhub.service.FichaExercicioService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/fichas-exercicios")
@Tag(name = "Ficha de Exercício", description = "Gerenciamento de fichas de exercícios dos alunos")
public class FichaExercicioController {

    private final FichaExercicioService fichaExercicioService;

    public FichaExercicioController(FichaExercicioService fichaExercicioService) {
        this.fichaExercicioService = fichaExercicioService;
    }

    @GetMapping
    @Operation(summary = "Lista todas as fichas de exercícios")
    public ResponseEntity<List<FichaExercicioDTO>> listar() {
        try {
            return ResponseEntity.ok(fichaExercicioService.listar());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma ficha de exercícios pelo ID")
    public ResponseEntity<FichaExercicioDTO> buscarPorId(@PathVariable Long id) {
        FichaExercicioDTO dto = fichaExercicioService.buscarPorId(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Cria uma nova ficha de exercícios para um aluno e instrutor existentes")
    public ResponseEntity<FichaExercicioDTO> criar(@Valid @RequestBody FichaExercicioDTO dto) {
        if (dto.alunoId() == null) {
            return ResponseEntity.badRequest().body(null); // aluno obrigatório
        }

        try {
            FichaExercicioDTO criado = fichaExercicioService.criar(dto);
            return ResponseEntity.ok(criado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma ficha de exercícios existente")
    public ResponseEntity<FichaExercicioDTO> atualizar(@PathVariable Long id, @Valid @RequestBody FichaExercicioDTO dto) {
        FichaExercicioDTO atualizado = fichaExercicioService.atualizar(id, dto);
        if (atualizado != null) {
            return ResponseEntity.ok(atualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma ficha de exercícios pelo ID")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (fichaExercicioService.deletar(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}