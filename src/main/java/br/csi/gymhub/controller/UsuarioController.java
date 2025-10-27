package br.csi.gymhub.controller;

import br.csi.gymhub.dto.UsuarioDTO;
import br.csi.gymhub.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Gerenciamento de usuários do sistema")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(
            summary = "Lista todos os usuários",
            description = "Retorna uma lista completa de todos os usuários cadastrados no sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuários listados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> listar() {
        try {
            List<UsuarioDTO> lista = usuarioService.listar();
            return ResponseEntity.ok(Map.of(
                    "status", 200,
                    "mensagem", "Usuários listados com sucesso",
                    "dados", lista
            ));
        } catch (Exception e) {
            logger.error("Erro ao listar usuários", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", 500,
                            "mensagem", "Erro ao listar os usuários. Tente novamente mais tarde."
                    ));
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Busca um usuário pelo ID",
            description = "Retorna o usuário correspondente ao ID fornecido. Retorna 404 se não encontrado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado com o ID fornecido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> buscarPorId(
            @Parameter(description = "ID do usuário a ser buscado", required = true)
            @PathVariable Long id
    ) {
        try {
            UsuarioDTO dto = usuarioService.buscarPorId(id);
            if (dto != null) {
                return ResponseEntity.ok(Map.of(
                        "status", 200,
                        "mensagem", "Usuário encontrado com sucesso",
                        "dados", dto
                ));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                                "status", 404,
                                "mensagem", "Usuário não encontrado com ID: " + id
                        ));
            }
        } catch (Exception e) {
            logger.error("Erro ao buscar usuário com ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", 500,
                            "mensagem", "Erro ao buscar o usuário. Contate o suporte se o problema persistir."
                    ));
        }
    }

    @PostMapping
    @Operation(
            summary = "Cria um novo usuário",
            description = "Cria um novo usuário no sistema e retorna os dados criados."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação do usuário"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> criar(@Valid @RequestBody UsuarioDTO dto) {
        try {
            UsuarioDTO criado = usuarioService.criar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", 201,
                    "mensagem", "Usuário criado com sucesso",
                    "dados", criado
            ));
        } catch (IllegalArgumentException e) {
            logger.warn("Dados inválidos ao criar usuário: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 400,
                    "mensagem", "Dados inválidos: " + e.getMessage()
            ));
        } catch (Exception e) {
            logger.error("Erro ao criar usuário", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", 500,
                            "mensagem", "Erro ao criar o usuário. Tente novamente mais tarde."
                    ));
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza um usuário existente",
            description = "Atualiza os dados de um usuário existente pelo ID. Retorna 404 se não encontrado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização do usuário"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado com o ID fornecido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> atualizar(
            @Parameter(description = "ID do usuário a ser atualizado", required = true)
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO dto
    ) {
        try {
            UsuarioDTO atualizado = usuarioService.atualizar(id, dto);
            if (atualizado != null) {
                return ResponseEntity.ok(Map.of(
                        "status", 200,
                        "mensagem", "Usuário atualizado com sucesso",
                        "dados", atualizado
                ));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                                "status", 404,
                                "mensagem", "Usuário não encontrado com ID: " + id
                        ));
            }
        } catch (IllegalArgumentException e) {
            logger.warn("Dados inválidos ao atualizar usuário: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 400,
                    "mensagem", "Dados inválidos: " + e.getMessage()
            ));
        } catch (Exception e) {
            logger.error("Erro ao atualizar usuário com ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", 500,
                            "mensagem", "Erro ao atualizar o usuário. Tente novamente mais tarde."
                    ));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deleta um usuário pelo ID",
            description = "Deleta um usuário existente pelo ID. Retorna 404 se não encontrado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado com o ID fornecido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> deletar(
            @Parameter(description = "ID do usuário a ser deletado", required = true)
            @PathVariable Long id
    ) {
        try {
            boolean deletado = usuarioService.deletar(id);
            if (deletado) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                                "status", 404,
                                "mensagem", "Usuário não encontrado com ID: " + id
                        ));
            }
        } catch (Exception e) {
            logger.error("Erro ao deletar usuário com ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", 500,
                            "mensagem", "Erro ao deletar o usuário. Contate o suporte se o problema persistir."
                    ));
        }
    }
}
