package com.estoque.sistema.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "estoque")
public class Estoque {

    @Id
    private String sku;

    @Column(nullable = false)
    private Long quantidade;

}
