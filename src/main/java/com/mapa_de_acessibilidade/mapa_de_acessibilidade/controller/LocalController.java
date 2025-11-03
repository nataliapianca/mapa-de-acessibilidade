package com.mapa_de_acessibilidade.mapa_de_acessibilidade.controller;

import com.mapa_de_acessibilidade.mapa_de_acessibilidade.model.Local;
import com.mapa_de_acessibilidade.mapa_de_acessibilidade.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

// Define a classe como um controlador REST
@RestController
// Mapeamento base para todos os endpoints deste controlador
@RequestMapping("/api/locais") 
public class LocalController {

    private final LocalService localService;

    // Injeção de Dependência do LocalService
    @Autowired
    public LocalController(LocalService localService) {
        this.localService = localService;
    }

    /* ----------------------------------
     * 1. C (CREATE) - Cadastrar Novo Local
     * Endpoint: POST /api/locais
     * ---------------------------------- */
    @PostMapping
    // O Front-end envia o Local e uma lista de IDs de Tags
    public ResponseEntity<Local> criarLocal(@RequestBody Local local, 
                                            @RequestParam Set<Long> tagIds) {
        try {
            // O service faz o trabalho de buscar as Tags e criar os LocalTag com score 0.0
            Local novoLocal = localService.salvarLocal(local, tagIds);
            
            // Retorna o Local criado com status 201 Created
            return new ResponseEntity<>(novoLocal, HttpStatus.CREATED);
        } catch (Exception e) {
            // Deve ser tratado com Controllers Advice mais robustos, mas por hora, retorna 500
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* ----------------------------------
     * 2. R (READ) - Buscar Todos os Locais
     * Endpoint: GET /api/locais
     * ---------------------------------- */
    @GetMapping
    public ResponseEntity<List<Local>> listarTodos() {
        List<Local> locais = localService.buscarTodos();
        return new ResponseEntity<>(locais, HttpStatus.OK);
    }

    /* ----------------------------------
     * 3. R (READ) - Buscar Local por ID
     * Endpoint: GET /api/locais/{id}
     * ---------------------------------- */
    @GetMapping("/{id}")
    public ResponseEntity<Local> buscarPorId(@PathVariable Long id) {
        return localService.buscarPorId(id)
                // Se Optional tiver um valor (Local encontrado), retorna 200 OK
                .map(local -> new ResponseEntity<>(local, HttpStatus.OK))
                // Se Optional estiver vazio (Local não encontrado), retorna 404 Not Found
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /* ----------------------------------
     * 4. U (UPDATE) - Atualizar Local Existente
     * Endpoint: PUT /api/locais/{id}
     * ---------------------------------- */
    @PutMapping("/{id}")
    public ResponseEntity<Local> atualizarLocal(@PathVariable Long id, 
                                                @RequestBody Local detalhesLocal,
                                                @RequestParam Set<Long> tagIds) {
        try {
            // O Service já trata a lógica de encontrar e atualizar
            Local localAtualizado = localService.atualizarLocal(id, detalhesLocal, tagIds);
            return new ResponseEntity<>(localAtualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Captura o erro do Service (Local não encontrado) e retorna 404
            if (e.getMessage().contains("Local não encontrado")) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /* ----------------------------------
     * 5. D (DELETE) - Deletar Local
     * Endpoint: DELETE /api/locais/{id}
     * ---------------------------------- */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLocal(@PathVariable Long id) {
        if (localService.buscarPorId(id).isPresent()) {
            localService.deletarLocal(id);
            // 204 No Content é o padrão para DELETE bem-sucedido
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
        }
        // Retorna 404 se tentar deletar algo que não existe
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}