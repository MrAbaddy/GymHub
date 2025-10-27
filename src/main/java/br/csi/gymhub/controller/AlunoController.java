package br.csi.gymhub.controller;

import br.csi.gymhub.dto.AlunoDTO;
import br.csi.gymhub.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alunos")
@PreAuthorize("isAuthenticated()")
@Tag(name = "Aluno", description = "Gerenciamento dos alunos e suas fichas físicas")
public class AlunoController {

    private static final Logger logger = LoggerFactory.getLogger(AlunoController.class);
    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    @Operation(summary = "Cria um novo aluno com ficha física")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Aluno criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> criar(@Valid @RequestBody AlunoDTO dto) {
        try {
            AlunoDTO alunoCriado = alunoService.criar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", 201,
                    "mensagem", "Aluno criado com sucesso",
                    "dados", alunoCriado
            ));
        } catch (IllegalArgumentException e) {
            logger.warn("Falha ao criar aluno: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 400,
                    "mensagem", "Dados inválidos: " + e.getMessage()
            ));
        } catch (Exception e) {
            logger.error("Erro ao criar aluno", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", 500,
                    "mensagem", "Falha ao criar o aluno. Tente novamente mais tarde."
            ));
        }
    }

    @GetMapping
    @Operation(summary = "Lista todos os alunos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alunos listados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> listar() {
        try {
            List<AlunoDTO> alunos = alunoService.listar();
            return ResponseEntity.ok(Map.of(
                    "status", 200,
                    "mensagem", "Alunos listados com sucesso",
                    "dados", alunos
            ));
        } catch (Exception e) {
            logger.error("Erro ao listar alunos", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", 500,
                    "mensagem", "Erro ao listar alunos. Tente novamente mais tarde."
            ));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca aluno por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Aluno encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado com o ID fornecido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            AlunoDTO aluno = alunoService.buscarPorId(id);
            if (aluno == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "status", 404,
                        "mensagem", "Aluno não encontrado com ID: " + id
                ));
            }
            return ResponseEntity.ok(Map.of(
                    "status", 200,
                    "mensagem", "Aluno encontrado com sucesso",
                    "dados", aluno
            ));
        } catch (Exception e) {
            logger.error("Erro ao buscar aluno com ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", 500,
                    "mensagem", "Erro ao buscar o aluno. Contate o suporte se o problema persistir."
            ));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um aluno e sua ficha física")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado com o ID fornecido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody AlunoDTO dto) {
        try {
            AlunoDTO alunoAtualizado = alunoService.atualizar(id, dto);
            if (alunoAtualizado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "status", 404,
                        "mensagem", "Aluno não encontrado com ID: " + id
                ));
            }
            return ResponseEntity.ok(Map.of(
                    "status", 200,
                    "mensagem", "Aluno atualizado com sucesso",
                    "dados", alunoAtualizado
            ));
        } catch (IllegalArgumentException e) {
            logger.warn("Dados inválidos ao atualizar aluno: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 400,
                    "mensagem", "Dados inválidos: " + e.getMessage()
            ));
        } catch (Exception e) {
            logger.error("Erro ao atualizar aluno com ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", 500,
                    "mensagem", "Erro ao atualizar o aluno. Tente novamente mais tarde."
            ));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um aluno e sua ficha física")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Aluno deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado com o ID fornecido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            boolean deletado = alunoService.deletar(id);
            if (!deletado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "status", 404,
                        "mensagem", "Aluno não encontrado com ID: " + id
                ));
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Erro ao deletar aluno com ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", 500,
                    "mensagem", "Erro ao deletar o aluno. Contate o suporte se o problema persistir."
            ));
        }
    }
}
