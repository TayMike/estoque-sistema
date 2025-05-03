package com.estoque.sistema.controller;

import com.estoque.sistema.entities.Estoque;
import com.estoque.sistema.services.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    EstoqueService estoqueService;

    @GetMapping("/{sku}")
    public ResponseEntity<Estoque> encontrarEstoque(@PathVariable String sku) {
        Optional<Estoque> estoque = estoqueService.encontrarEstoque(sku);
        return estoque.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Estoque>> encontrarEstoque() {
        List<Estoque> estoque = estoqueService.encontrarEstoque();
        if (estoque.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(estoque);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Estoque> cadastrarEstoque(@RequestBody Estoque estoque) {
        try {
            Estoque estoqueCadastrado = estoqueService.cadastrarEstoque(estoque);
            return ResponseEntity.ok(estoqueCadastrado);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/alterar")
    public ResponseEntity<Estoque> alterarEstoque(@RequestBody Estoque estoque) {
        try {
            Estoque estoqueAlterado = estoqueService.alterarEstoque(estoque);
            return ResponseEntity.ok(estoqueAlterado);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/deletar/{sku}")
    public ResponseEntity<Estoque> deletarEstoque(@PathVariable String sku) {
        try {
            estoqueService.deletarEstoque(sku);
            return ResponseEntity.ok().build();
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
