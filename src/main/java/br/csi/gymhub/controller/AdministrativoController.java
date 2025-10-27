package br.csi.gymhub.controller;

import br.csi.gymhub.dto.AdministrativoDTO;
import br.csi.gymhub.service.AdministrativoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administrativo")
@Tag(name = "Administrativo", description = "Gerencia dados administrativos vinculados a alunos")
public class AdministrativoController {

    private final AdministrativoService administrativoService;

    public AdministrativoController(AdministrativoService administrativoService) {
        this.administrativoService = administrativoService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os registros administrativos")
    public ResponseEntity<?> listar() {
        try {
            List<AdministrativoDTO> lista = administrativoService.listar();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Não foi possível listar os registros administrativos. Por favor, tente novamente mais tarde.");
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um registro administrativo pelo ID")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            AdministrativoDTO dto = administrativoService.buscarPorId(id);
            if (dto != null) {
                return ResponseEntity.ok(dto);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nenhum registro administrativo encontrado com o ID informado: " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar o registro administrativo. Contate o suporte se o problema persistir.");
        }
    }

    @PostMapping
    @Operation(summary = "Cria um registro administrativo vinculado a um aluno existente")
    public ResponseEntity<?> criar(@Valid @RequestBody AdministrativoDTO dto) {
        try {
            if (dto.alunoId() == null) {
                return ResponseEntity.badRequest().body("O campo 'alunoId' é obrigatório e não pode ser nulo.");
            }
            AdministrativoDTO criado = administrativoService.criar(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Registro administrativo criado com sucesso: " + criado);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Dados inválidos para criação do registro administrativo: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Falha ao criar o registro administrativo. Por favor, tente novamente mais tarde.");
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um registro administrativo existente")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody AdministrativoDTO dto) {
        try {
            AdministrativoDTO atualizado = administrativoService.atualizar(id, dto);
            if (atualizado != null) {
                return ResponseEntity.ok("Registro administrativo atualizado com sucesso: " + atualizado);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Não foi possível atualizar. Registro administrativo com ID " + id + " não encontrado.");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Dados inválidos para atualização do registro administrativo: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar o registro administrativo. Por favor, tente novamente mais tarde.");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um registro administrativo pelo ID")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            if (administrativoService.deletar(id)) {
                return ResponseEntity.ok("Registro administrativo deletado com sucesso.");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Não foi possível deletar. Registro administrativo com ID " + id + " não encontrado.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar o registro administrativo. Contate o suporte se o problema persistir.");
        }
    }
}
