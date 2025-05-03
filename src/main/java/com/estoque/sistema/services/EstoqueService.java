package com.estoque.sistema.services;

import com.estoque.sistema.entities.Estoque;
import com.estoque.sistema.repositories.EstoqueRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EstoqueService {

    @Autowired
    EstoqueRepository estoqueRepository;

    public Optional<Estoque> encontrarEstoque(String sku) {
        return estoqueRepository.findById(sku);
    }

    public List<Estoque> encontrarEstoque() {
        return estoqueRepository.findAll();
    }

    public Estoque cadastrarEstoque(Estoque estoque) {
        return estoqueRepository.save(estoque);
    }

    public Estoque alterarEstoque(Estoque estoqueNovo) {
        if(estoqueRepository.existsById(estoqueNovo.getSku())) {
            Estoque estoqueVelho = estoqueRepository.getReferenceById(estoqueNovo.getSku());
            estoqueVelho.setQuantidade(estoqueNovo.getQuantidade());
            return estoqueRepository.save(estoqueVelho);
        }
        return null;
    }

    public void deletarEstoque(String estoque) {
        estoqueRepository.deleteById(estoque);
    }

}
