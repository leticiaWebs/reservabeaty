package com.reservabeaty.reservabeaty.usecases.services;

import com.reservabeaty.reservabeaty.domain.models.Cliente;
import com.reservabeaty.reservabeaty.domain.repository.ClienteRepository;
import com.reservabeaty.reservabeaty.usecase.service.ClienteService;
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

class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository repository;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cliente = new Cliente(
                1L,
                "Leticia",
                "leticia@email.com",
                "11999999999"
        );
    }

    @Test
    void testCriarClienteComSucesso() {
        when(repository.findByEmail(cliente.getEmail())).thenReturn(Optional.empty());
        when(repository.save(cliente)).thenReturn(cliente);

        Cliente criado = clienteService.criar(cliente);

        assertNotNull(criado);
        assertEquals("Leticia", criado.getNome());
        verify(repository).findByEmail(cliente.getEmail());
        verify(repository).save(cliente);
    }

    @Test
    void testCriarClienteComEmailDuplicado() {
        when(repository.findByEmail(cliente.getEmail())).thenReturn(Optional.of(cliente));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.criar(cliente);
        });

        assertEquals("Email já cadastrado", exception.getMessage());
        verify(repository).findByEmail(cliente.getEmail());
        verify(repository, never()).save(any(Cliente.class));
    }

    @Test
    void testListarTodosClientes() {
        List<Cliente> lista = Arrays.asList(cliente);
        when(repository.findAll()).thenReturn(lista);

        List<Cliente> result = clienteService.listarTodos();

        assertEquals(1, result.size());
        assertEquals(cliente, result.get(0));
        verify(repository).findAll();
    }

    @Test
    void testBuscarPorIdExistente() {
        when(repository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente encontrado = clienteService.buscarPorId(1L);

        assertNotNull(encontrado);
        assertEquals(1L, encontrado.getId());
        verify(repository).findById(1L);
    }

    @Test
    void testBuscarPorIdNaoExistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.buscarPorId(1L);
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
        verify(repository).findById(1L);
    }

    @Test
    void testAtualizarClienteComSucesso() {
        Cliente atualizado = new Cliente();
        atualizado.setNome("Leticia Oliveira");
        atualizado.setEmail("leticia.oliveira@email.com");
        atualizado.setTelefone("11988888888");

        when(repository.findById(1L)).thenReturn(Optional.of(cliente));
        when(repository.save(any(Cliente.class))).thenAnswer(i -> i.getArgument(0));

        Cliente result = clienteService.atualizar(1L, atualizado);

        assertEquals("Leticia Oliveira", result.getNome());
        assertEquals("leticia.oliveira@email.com", result.getEmail());
        assertEquals("11988888888", result.getTelefone());
        verify(repository).findById(1L);
        verify(repository).save(cliente);
    }

    @Test
    void testDeletarCliente() {
        doNothing().when(repository).deleteById(1L);

        clienteService.deletar(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void testBuscarPorNome() {
        List<Cliente> lista = Arrays.asList(cliente);
        when(repository.findByNomeContainingIgnoreCase("Leticia")).thenReturn(lista);

        List<Cliente> result = clienteService.buscarPorNome("Leticia");

        assertEquals(1, result.size());
        assertEquals("Leticia", result.get(0).getNome());
        verify(repository).findByNomeContainingIgnoreCase("Leticia");
    }
}
