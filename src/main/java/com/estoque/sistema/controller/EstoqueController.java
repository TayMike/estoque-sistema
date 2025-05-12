package com.estoque.sistema.controller;

import com.estoque.sistema.entities.Estoque;
import com.estoque.sistema.services.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    EstoqueService estoqueService;

    @GetMapping("/{sku}")
    public ResponseEntity<Estoque> encontrarProduto(@PathVariable String sku) {
        Optional<Estoque> estoque = estoqueService.encontrarEstoque(sku);
        if (estoque.isPresent()) {
            return ResponseEntity.ok(estoque.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
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
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{sku}")
                    .buildAndExpand(estoqueCadastrado.getSku())
                    .toUri();
            return ResponseEntity.created(uri).body(estoqueCadastrado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/alterar")
    public ResponseEntity<Estoque> alterarEstoque(@RequestBody Estoque estoque) {
        Optional<Estoque> estoqueVerificado = estoqueService.encontrarEstoque(estoque.getSku());
        if (estoqueVerificado.isPresent()) {
            Estoque estoqueAlterado = estoqueService.alterarEstoque(estoque);
            return ResponseEntity.ok(estoqueAlterado);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{sku}")
    public ResponseEntity<Estoque> deletarEstoque(@PathVariable String sku) {
        Optional<Estoque> estoque = estoqueService.encontrarEstoque(sku);
        if (estoque.isPresent()) {
            estoqueService.deletarEstoque(sku);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
