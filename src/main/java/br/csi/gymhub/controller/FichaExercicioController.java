package br.csi.gymhub.controller;

import br.csi.gymhub.dto.FichaExercicioDTO;
import br.csi.gymhub.service.FichaExercicioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/fichas-exercicios")
@Tag(name = "Ficha de Exercício", description = "Gerenciamento de fichas de exercícios dos alunos")
public class FichaExercicioController {

    private static final Logger logger = LoggerFactory.getLogger(FichaExercicioController.class);
    private final FichaExercicioService fichaExercicioService;

    public FichaExercicioController(FichaExercicioService fichaExercicioService) {
        this.fichaExercicioService = fichaExercicioService;
    }

    @GetMapping
    @Operation(summary = "Lista todas as fichas de exercícios")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fichas de exercícios listadas com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> listar() {
        try {
            List<FichaExercicioDTO> lista = fichaExercicioService.listar();
            return ResponseEntity.ok(Map.of(
                    "status", 200,
                    "mensagem", "Fichas de exercícios listadas com sucesso",
                    "dados", lista
            ));
        } catch (Exception e) {
            logger.error("Erro ao listar fichas de exercícios", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", 500,
                            "mensagem", "Erro ao listar as fichas de exercícios. Tente novamente mais tarde."
                    ));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma ficha de exercícios pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ficha de exercícios encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ficha de exercícios não encontrada com o ID fornecido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            FichaExercicioDTO dto = fichaExercicioService.buscarPorId(id);
            if (dto != null) {
                return ResponseEntity.ok(Map.of(
                        "status", 200,
                        "mensagem", "Ficha de exercícios encontrada com sucesso",
                        "dados", dto
                ));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                                "status", 404,
                                "mensagem", "Ficha de exercícios não encontrada com ID: " + id
                        ));
            }
        } catch (Exception e) {
            logger.error("Erro ao buscar ficha de exercícios com ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", 500,
                            "mensagem", "Erro ao buscar a ficha de exercícios. Contate o suporte se o problema persistir."
                    ));
        }
    }

    @PostMapping
    @Operation(summary = "Cria uma nova ficha de exercícios para um aluno e instrutor existentes")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ficha de exercícios criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> criar(@Valid @RequestBody FichaExercicioDTO dto) {
        if (dto.alunoId() == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 400,
                    "mensagem", "O campo 'alunoId' é obrigatório e não pode ser nulo."
            ));
        }

        try {
            FichaExercicioDTO criado = fichaExercicioService.criar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", 201,
                    "mensagem", "Ficha de exercícios criada com sucesso",
                    "dados", criado
            ));
        } catch (IllegalArgumentException e) {
            logger.warn("Falha ao criar ficha de exercícios: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 400,
                    "mensagem", "Dados inválidos: " + e.getMessage()
            ));
        } catch (Exception e) {
            logger.error("Erro ao criar ficha de exercícios", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", 500,
                            "mensagem", "Erro ao criar a ficha de exercícios. Tente novamente mais tarde."
                    ));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma ficha de exercícios existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ficha de exercícios atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Ficha de exercícios não encontrada com o ID fornecido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody FichaExercicioDTO dto) {
        try {
            FichaExercicioDTO atualizado = fichaExercicioService.atualizar(id, dto);
            if (atualizado != null) {
                return ResponseEntity.ok(Map.of(
                        "status", 200,
                        "mensagem", "Ficha de exercícios atualizada com sucesso",
                        "dados", atualizado
                ));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                                "status", 404,
                                "mensagem", "Ficha de exercícios não encontrada com ID: " + id
                        ));
            }
        } catch (IllegalArgumentException e) {
            logger.warn("Dados inválidos ao atualizar ficha de exercícios: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 400,
                    "mensagem", "Dados inválidos: " + e.getMessage()
            ));
        } catch (Exception e) {
            logger.error("Erro ao atualizar ficha de exercícios com ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", 500,
                            "mensagem", "Erro ao atualizar a ficha de exercícios. Tente novamente mais tarde."
                    ));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma ficha de exercícios pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ficha de exercícios deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ficha de exercícios não encontrada com o ID fornecido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            boolean deletado = fichaExercicioService.deletar(id);
            if (deletado) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                                "status", 404,
                                "mensagem", "Ficha de exercícios não encontrada com ID: " + id
                        ));
            }
        } catch (Exception e) {
            logger.error("Erro ao deletar ficha de exercícios com ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", 500,
                            "mensagem", "Erro ao deletar a ficha de exercícios. Contate o suporte se o problema persistir."
                    ));
        }
    }
}
