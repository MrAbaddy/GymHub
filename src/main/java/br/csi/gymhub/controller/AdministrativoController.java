package br.csi.gymhub.controller;

import br.csi.gymhub.dto.AdministrativoDTO;
import br.csi.gymhub.service.AdministrativoService;
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
@RequestMapping("/administrativo")
@Tag(name = "Administrativo", description = "Gerencia dados administrativos vinculados a alunos")
public class AdministrativoController {

    private static final Logger logger = LoggerFactory.getLogger(AdministrativoController.class);
    private final AdministrativoService administrativoService;

    public AdministrativoController(AdministrativoService administrativoService) {
        this.administrativoService = administrativoService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os registros administrativos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registros administrativos listados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> listar() {
        try {
            List<AdministrativoDTO> lista = administrativoService.listar();
            return ResponseEntity.ok(Map.of(
                    "status", 200,
                    "mensagem", "Registros administrativos listados com sucesso",
                    "dados", lista
            ));
        } catch (Exception e) {
            logger.error("Erro ao listar registros administrativos", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", 500,
                            "mensagem", "Não foi possível listar os registros administrativos. Tente novamente mais tarde."
                    ));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um registro administrativo pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro administrativo encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro administrativo não encontrado com o ID fornecido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            AdministrativoDTO dto = administrativoService.buscarPorId(id);
            if (dto != null) {
                return ResponseEntity.ok(Map.of(
                        "status", 200,
                        "mensagem", "Registro administrativo encontrado com sucesso",
                        "dados", dto
                ));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "status", 404,
                            "mensagem", "Nenhum registro administrativo encontrado com ID: " + id
                    ));
        } catch (Exception e) {
            logger.error("Erro ao buscar registro administrativo com ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", 500,
                            "mensagem", "Erro ao buscar o registro administrativo. Contate o suporte se o problema persistir."
                    ));
        }
    }

    @PostMapping
    @Operation(summary = "Cria um registro administrativo vinculado a um aluno existente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro administrativo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> criar(@Valid @RequestBody AdministrativoDTO dto) {
        if (dto.alunoId() == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 400,
                    "mensagem", "O campo 'alunoId' é obrigatório e não pode ser nulo."
            ));
        }

        try {
            AdministrativoDTO criado = administrativoService.criar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", 201,
                    "mensagem", "Registro administrativo criado com sucesso",
                    "dados", criado
            ));
        } catch (IllegalArgumentException e) {
            logger.warn("Dados inválidos ao criar registro administrativo: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 400,
                    "mensagem", "Dados inválidos: " + e.getMessage()
            ));
        } catch (Exception e) {
            logger.error("Erro ao criar registro administrativo", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", 500,
                    "mensagem", "Falha ao criar o registro administrativo. Tente novamente mais tarde."
            ));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um registro administrativo existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro administrativo atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Registro administrativo não encontrado com o ID fornecido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody AdministrativoDTO dto) {
        try {
            AdministrativoDTO atualizado = administrativoService.atualizar(id, dto);
            if (atualizado != null) {
                return ResponseEntity.ok(Map.of(
                        "status", 200,
                        "mensagem", "Registro administrativo atualizado com sucesso",
                        "dados", atualizado
                ));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", 404,
                    "mensagem", "Registro administrativo não encontrado com ID: " + id
            ));
        } catch (IllegalArgumentException e) {
            logger.warn("Dados inválidos ao atualizar registro administrativo: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 400,
                    "mensagem", "Dados inválidos: " + e.getMessage()
            ));
        } catch (Exception e) {
            logger.error("Erro ao atualizar registro administrativo com ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", 500,
                    "mensagem", "Erro ao atualizar o registro administrativo. Tente novamente mais tarde."
            ));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um registro administrativo pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Registro administrativo deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro administrativo não encontrado com o ID fornecido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            boolean deletado = administrativoService.deletar(id);
            if (deletado) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", 404,
                    "mensagem", "Registro administrativo não encontrado com ID: " + id
            ));
        } catch (Exception e) {
            logger.error("Erro ao deletar registro administrativo com ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", 500,
                    "mensagem", "Erro ao deletar o registro administrativo. Contate o suporte se o problema persistir."
            ));
        }
    }
}