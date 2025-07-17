package com.reservabeaty.reservabeaty.application.usecase.service;

import com.reservabeaty.reservabeaty.domain.models.Endereco;
import com.reservabeaty.reservabeaty.domain.repository.EnderecoRepository;
import com.reservabeaty.reservabeaty.application.usecase.service.EnderecoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private EnderecoRepository repository;

    private Endereco endereco;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        endereco = new Endereco(1L, "Rua A", "123", "Bairro B", "Cidade C", "Estado E", "12345678");
    }

    @Test
    void testCriarEnderecoComSucesso() {
        when(repository.findByCep(endereco.getCep())).thenReturn(Optional.empty());
        when(repository.save(endereco)).thenReturn(endereco);

        Endereco criado = enderecoService.criar(endereco);

        assertNotNull(criado);
        assertEquals("Rua A", criado.getRua());
        verify(repository).findByCep(endereco.getCep());
        verify(repository).save(endereco);
    }

    @Test
    void testCriarEnderecoComCepDuplicado() {
        when(repository.findByCep(endereco.getCep())).thenReturn(Optional.of(endereco));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            enderecoService.criar(endereco);
        });

        assertEquals("Endereço com este CEP já cadastrado.", exception.getMessage());
        verify(repository).findByCep(endereco.getCep());
        verify(repository, never()).save(any());
    }

    @Test
    void testListarTodosEnderecos() {
        List<Endereco> lista = Arrays.asList(endereco);
        when(repository.findAll()).thenReturn(lista);

        List<Endereco> result = enderecoService.listarTodos();

        assertEquals(1, result.size());
        assertEquals(endereco, result.get(0));
        verify(repository).findAll();
    }

    @Test
    void testBuscarPorIdExistente() {
        when(repository.findById(1L)).thenReturn(Optional.of(endereco));

        Endereco encontrado = enderecoService.buscarPorId(1L);

        assertNotNull(encontrado);
        assertEquals(1L, encontrado.getId());
        verify(repository).findById(1L);
    }

    @Test
    void testBuscarPorIdNaoExistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            enderecoService.buscarPorId(1L);
        });

        assertEquals("Endereço não encontrado", exception.getMessage());
        verify(repository).findById(1L);
    }

    @Test
    void testAtualizarEnderecoComSucesso() {
        Endereco atualizado = new Endereco(null, "Rua X", "999", "Bairro Y", "Cidade Z", "Estado W", "87654321");

        when(repository.findById(1L)).thenReturn(Optional.of(endereco));
        when(repository.save(any(Endereco.class))).thenAnswer(i -> i.getArgument(0));

        Endereco result = enderecoService.atualizar(1L, atualizado);

        assertEquals("Rua X", result.getRua());
        assertEquals("999", result.getNumero());
        assertEquals("87654321", result.getCep());
        verify(repository).findById(1L);
        verify(repository).save(endereco);
    }

    @Test
    void testDeletarEndereco() {
        doNothing().when(repository).deleteById(1L);

        enderecoService.deletar(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void testBuscarPorCidade() {
        List<Endereco> lista = Arrays.asList(endereco);
        when(repository.findByCidadeContainingIgnoreCase("Cidade C")).thenReturn(lista);

        List<Endereco> result = enderecoService.buscarPorCidade("Cidade C");

        assertEquals(1, result.size());
        verify(repository).findByCidadeContainingIgnoreCase("Cidade C");
    }

    @Test
    void testBuscarPorBairro() {
        List<Endereco> lista = Arrays.asList(endereco);
        when(repository.findByBairroContainingIgnoreCase("Bairro B")).thenReturn(lista);

        List<Endereco> result = enderecoService.buscarPorBairro("Bairro B");

        assertEquals(1, result.size());
        verify(repository).findByBairroContainingIgnoreCase("Bairro B");
    }

    @Test
    void testBuscarPorCepExistente() {
        when(repository.findByCep("12345678")).thenReturn(Optional.of(endereco));

        Endereco result = enderecoService.buscarPorCep("12345678");

        assertNotNull(result);
        assertEquals("12345678", result.getCep());
        verify(repository).findByCep("12345678");
    }

    @Test
    void testBuscarPorCepNaoExistente() {
        when(repository.findByCep("00000000")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            enderecoService.buscarPorCep("00000000");
        });

        assertEquals("CEP não encontrado", exception.getMessage());
        verify(repository).findByCep("00000000");
    }
}
