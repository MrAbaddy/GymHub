package br.csi.gymhub.controller;

import br.csi.gymhub.dto.AlunoDTO;
import br.csi.gymhub.service.AlunoService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/alunos")
@PreAuthorize("isAuthenticated()")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @Operation(summary = "Cria um novo aluno com ficha física")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Aluno criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<AlunoDTO> criar(@Valid @RequestBody AlunoDTO dto) {
        try {
            AlunoDTO alunoCriado = alunoService.criar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(alunoCriado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Lista todos os alunos")
    @GetMapping
    public ResponseEntity<List<AlunoDTO>> listar() {
        List<AlunoDTO> alunos = alunoService.listar();
        return ResponseEntity.ok(alunos);
    }

    @Operation(summary = "Busca aluno por ID")
    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> buscarPorId(@PathVariable Long id) {
        AlunoDTO aluno = alunoService.buscarPorId(id);
        if (aluno == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(aluno);
    }

    @Operation(summary = "Atualiza um aluno e sua ficha física")
    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody AlunoDTO dto) {
        AlunoDTO alunoAtualizado = alunoService.atualizar(id, dto);
        if (alunoAtualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(alunoAtualizado);
    }

    @Operation(summary = "Deleta um aluno e sua ficha física")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean deletado = alunoService.deletar(id);
        if (!deletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}