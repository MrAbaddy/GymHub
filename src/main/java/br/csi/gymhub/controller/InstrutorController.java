package br.csi.gymhub.controller;

import br.csi.gymhub.dto.InstrutorDTO;
import br.csi.gymhub.service.InstrutorService;
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
@RequestMapping("/instrutores")
@Tag(name = "Instrutor", description = "Gerenciamento dos instrutores da academia")
public class InstrutorController {

    private static final Logger logger = LoggerFactory.getLogger(InstrutorController.class);
    private final InstrutorService instrutorService;

    public InstrutorController(InstrutorService instrutorService) {
        this.instrutorService = instrutorService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os instrutores")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Instrutores listados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> listar() {
        try {
            List<InstrutorDTO> lista = instrutorService.listar();
            return ResponseEntity.ok(Map.of(
                    "status", 200,
                    "mensagem", "Instrutores listados com sucesso",
                    "dados", lista
            ));
        } catch (Exception e) {
            logger.error("Erro ao listar instrutores", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", 500,
                            "mensagem", "Erro ao listar os instrutores. Tente novamente mais tarde."
                    ));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um instrutor pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Instrutor encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Instrutor não encontrado com o ID fornecido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            InstrutorDTO dto = instrutorService.buscarPorId(id);
            if (dto != null) {
                return ResponseEntity.ok(Map.of(
                        "status", 200,
                        "mensagem", "Instrutor encontrado com sucesso",
                        "dados", dto
                ));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                                "status", 404,
                                "mensagem", "Instrutor não encontrado com ID: " + id
                        ));
            }
        } catch (Exception e) {
            logger.error("Erro ao buscar instrutor com ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", 500,
                            "mensagem", "Erro ao buscar o instrutor. Contate o suporte se o problema persistir."
                    ));
        }
    }

    @PostMapping
    @Operation(summary = "Cria um novo instrutor")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Instrutor criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> criar(@Valid @RequestBody InstrutorDTO dto) {
        try {
            InstrutorDTO criado = instrutorService.criar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", 201,
                    "mensagem", "Instrutor criado com sucesso",
                    "dados", criado
            ));
        } catch (IllegalArgumentException e) {
            logger.warn("Dados inválidos ao criar instrutor: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 400,
                    "mensagem", "Dados inválidos: " + e.getMessage()
            ));
        } catch (Exception e) {
            logger.error("Erro ao criar instrutor", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", 500,
                            "mensagem", "Erro ao criar o instrutor. Tente novamente mais tarde."
                    ));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um instrutor existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Instrutor atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Instrutor não encontrado com o ID fornecido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody InstrutorDTO dto) {
        try {
            InstrutorDTO atualizado = instrutorService.atualizar(id, dto);
            if (atualizado != null) {
                return ResponseEntity.ok(Map.of(
                        "status", 200,
                        "mensagem", "Instrutor atualizado com sucesso",
                        "dados", atualizado
                ));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                                "status", 404,
                                "mensagem", "Instrutor não encontrado com ID: " + id
                        ));
            }
        } catch (IllegalArgumentException e) {
            logger.warn("Dados inválidos ao atualizar instrutor: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 400,
                    "mensagem", "Dados inválidos: " + e.getMessage()
            ));
        } catch (Exception e) {
            logger.error("Erro ao atualizar instrutor com ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", 500,
                            "mensagem", "Erro ao atualizar o instrutor. Tente novamente mais tarde."
                    ));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um instrutor pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Instrutor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Instrutor não encontrado com o ID fornecido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            boolean deletado = instrutorService.deletar(id);
            if (deletado) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                                "status", 404,
                                "mensagem", "Instrutor não encontrado com ID: " + id
                        ));
            }
        } catch (Exception e) {
            logger.error("Erro ao deletar instrutor com ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", 500,
                            "mensagem", "Erro ao deletar o instrutor. Contate o suporte se o problema persistir."
                    ));
        }
    }
}
