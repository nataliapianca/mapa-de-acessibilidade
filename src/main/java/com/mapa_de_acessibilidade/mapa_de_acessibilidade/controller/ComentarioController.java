package com.mapa_de_acessibilidade.mapa_de_acessibilidade.controller;

import com.mapa_de_acessibilidade.mapa_de_acessibilidade.model.ComentarioUsuario;
import com.mapa_de_acessibilidade.mapa_de_acessibilidade.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

    @Autowired
    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    // pra criar um novo coment
    @PostMapping("/{localId}")
    public ResponseEntity<ComentarioUsuario> criarComentario(
            @PathVariable Long localId,
            @RequestBody ComentarioUsuario comentario,
            @RequestParam Set<Long> tagIds) {
        try {
            ComentarioUsuario novoComentario = comentarioService.salvarComentario(localId, comentario, tagIds);
            return new ResponseEntity<>(novoComentario, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // buscar os comentarios por local
    @GetMapping("/local/{localId}")
    public ResponseEntity<List<ComentarioUsuario>> listarPorLocal(@PathVariable Long localId) {
        List<ComentarioUsuario> comentarios = comentarioService.buscarPorLocal(localId);
        if (comentarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(comentarios, HttpStatus.OK);
    }

    // deletar comentario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarComentario(@PathVariable Long id) {
        try {
            comentarioService.deletarComentario(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}