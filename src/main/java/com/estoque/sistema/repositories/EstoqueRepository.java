package com.estoque.sistema.repositories;

import com.estoque.sistema.entities.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, String> {
}
