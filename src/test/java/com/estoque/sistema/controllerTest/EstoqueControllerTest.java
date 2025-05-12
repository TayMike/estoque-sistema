package com.estoque.sistema.controllerTest;

import com.estoque.sistema.controller.EstoqueController;
import com.estoque.sistema.entities.Estoque;
import com.estoque.sistema.services.EstoqueService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EstoqueControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EstoqueService estoqueService;

    @InjectMocks
    private EstoqueController estoqueController;

    private String sku = "ONE0001";
    private Estoque estoque;
    private AutoCloseable openMocks;
    private String json = """
            {
                "sku": "ONE0001",
                "quantidade": 10
            }
            """;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(estoqueController).build();
        estoque = Estoque.builder()
                .sku(sku)
                .quantidade(10L)
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void testEncontrarEstoque_Success() throws Exception {
        // Arrange
        when(estoqueService.encontrarEstoque(estoque.getSku())).thenReturn(Optional.of(estoque));

        // Act & Assert
        mockMvc.perform(get("/estoque/{sku}", sku))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value("ONE0001"));
        verify(estoqueService, times(1)).encontrarEstoque(sku);
    }

    @Test
    void testEncontrarEstoque_NotFound() throws Exception {
        // Arrange
        when(estoqueService.encontrarEstoque(estoque.getSku())).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/estoque/{sku}", sku))
                .andExpect(status().isNotFound());
        verify(estoqueService, times(1)).encontrarEstoque(sku);
    }

    @Test
    void testEncontrarTodos_Success() throws Exception {
        // Arrange
        when(estoqueService.encontrarEstoque()).thenReturn(List.of(estoque));

        // Act & Assert
        mockMvc.perform(get("/estoque/todos", sku))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sku").value("ONE0001"));
        verify(estoqueService, times(1)).encontrarEstoque();
    }

    @Test
    void testEncontrarTodos_NotFound() throws Exception {
        // Arrange
        when(estoqueService.encontrarEstoque()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/estoque/todos", sku))
                .andExpect(status().isNotFound());
        verify(estoqueService, times(1)).encontrarEstoque();
    }

    @Test
    void testCadastrarEstoque_Success() throws Exception {
        // Arrange
        when(estoqueService.cadastrarEstoque(any(Estoque.class))).thenReturn(estoque);

        // Act & Assert
        mockMvc.perform(post("/estoque/cadastrar")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().json(json));
        verify(estoqueService, times(1)).cadastrarEstoque(any(Estoque.class));
    }

    @Test
    void testCadastrarEstoque_BadRequest() throws Exception {
        // Arrange
        when(estoqueService.cadastrarEstoque(any(Estoque.class))).thenThrow(new IllegalArgumentException("Dados inválidos"));

        // Act & Assert
        mockMvc.perform(post("/estoque/cadastrar")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest());
        verify(estoqueService, times(1)).cadastrarEstoque(any(Estoque.class));
    }

    @Test
    void testAtualizarEstoque_Success() throws Exception {
        // Arrange
        when(estoqueService.encontrarEstoque(sku)).thenReturn(Optional.of(estoque));
        when(estoqueService.alterarEstoque(any(Estoque.class))).thenReturn(estoque);

        // Act & Assert
        mockMvc.perform(put("/estoque/alterar")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(estoqueService, times(1)).alterarEstoque(any(Estoque.class));
    }

    @Test
    void testAtualizarEstoque_BadRequest() throws Exception {
        // Arrange
        when(estoqueService.encontrarEstoque(sku)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(put("/estoque/alterar")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest());
        verify(estoqueService, times(0)).alterarEstoque(any(Estoque.class));
    }

    @Test
    void testDeletarEstoque_Success() throws Exception {
        // Arrange
        when(estoqueService.encontrarEstoque(estoque.getSku())).thenReturn(Optional.of(estoque));

        // Act & Assert
        mockMvc.perform(delete("/estoque/{sku}", sku))
                .andExpect(status().isNoContent());
        verify(estoqueService, times(1)).deletarEstoque(sku);
    }

    @Test
    void testDeletarEstoque_BadRequest() throws Exception {
        // Arrange
        when(estoqueService.encontrarEstoque(estoque.getSku())).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(delete("/estoque/{sku}", sku))
                .andExpect(status().isBadRequest());
        verify(estoqueService, times(0)).deletarEstoque(sku);
    }

}
