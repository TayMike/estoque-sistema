package com.estoque.sistema.servicesTest;

import com.estoque.sistema.entities.Estoque;
import com.estoque.sistema.repositories.EstoqueRepository;
import com.estoque.sistema.services.EstoqueService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EstoqueServiceTest {

    @Mock
    private EstoqueRepository estoqueRepository;

    @InjectMocks
    private EstoqueService estoqueService;

    private String sku = "ONE0001";
    private AutoCloseable openMocks;
    private Estoque estoque;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);

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
    void testEncontrarEstoque() {
        // Arrange
        when(estoqueRepository.findById(estoque.getSku())).thenReturn(Optional.of(estoque));

        // Act
        Optional<Estoque> resultado = estoqueService.encontrarEstoque(estoque.getSku());

        // Assert
        assertNotNull(resultado);
        assertEquals(estoque.getSku(), resultado.get().getSku());
        verify(estoqueRepository, times(1)).findById(estoque.getSku());
    }

    @Test
    void testEncontrarTodosEstoque() {
        // Arrange
        when(estoqueRepository.findAll()).thenReturn(List.of(estoque));

        // Act
        List<Estoque> resultado = estoqueService.encontrarEstoque();

        // Assert
        assertNotNull(resultado);
        assertEquals(estoque.getSku(), resultado.getFirst().getSku());
        verify(estoqueRepository, times(1)).findAll();
    }

    @Test
    void testCadastrarEstoque() {
        // Arrange
        when(estoqueRepository.save(any(Estoque.class))).thenReturn(estoque);

        // Act
        Estoque resultado = estoqueService.cadastrarEstoque(estoque);

        // Assert
        assertNotNull(resultado);
        assertEquals(estoque.getSku(), resultado.getSku());
        verify(estoqueRepository, times(1)).save(estoque);
    }

    @Test
    void testAtualizarEstoque_Success() {
        // Arrange
        when(estoqueRepository.existsById(estoque.getSku())).thenReturn(true);
        when(estoqueRepository.getReferenceById(estoque.getSku())).thenReturn(estoque);
        when(estoqueRepository.save(any(Estoque.class))).thenReturn(estoque);

        // Act
        Estoque resultado = estoqueService.alterarEstoque(estoque);

        // Assert
        assertNotNull(resultado);
        verify(estoqueRepository, times(1)).save(estoque);
    }

    @Test
    void testAtualizarEstoque_Null() {
        // Arrange
        when(estoqueRepository.existsById(estoque.getSku())).thenReturn(false);

        // Act
        Estoque resultado = estoqueService.alterarEstoque(estoque);

        // Assert
        assertNull(resultado);
        verify(estoqueRepository, times(0)).save(estoque);
    }

    @Test
    void testDeletarEstoque() {
        // Arrange & Act
        estoqueService.deletarEstoque(estoque.getSku());

        // Assert
        verify(estoqueRepository, times(1)).deleteById(estoque.getSku());
    }

}
