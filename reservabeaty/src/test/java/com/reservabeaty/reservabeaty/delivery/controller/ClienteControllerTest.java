package com.reservabeaty.reservabeaty.delivery.controller;

import com.reservabeaty.reservabeaty.domain.models.Cliente;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import com.reservabeaty.reservabeaty.usecase.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

class ClienteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClienteService service;

    @InjectMocks
    private ClienteController controller;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testCriarCliente() throws Exception {
        Cliente cliente = new Cliente(null, "João Silva", "joao@email.com", "11999999999");
        Cliente clienteRetorno = new Cliente(1L, "João Silva", "joao@email.com", "11999999999");

        when(service.criar(any(Cliente.class))).thenReturn(clienteRetorno);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João Silva"));

        verify(service).criar(any(Cliente.class));
    }

    @Test
    void testListarTodos() throws Exception {
        Cliente c1 = new Cliente(1L, "João Silva", "joao@email.com", "11999999999");
        Cliente c2 = new Cliente(2L, "Maria Oliveira", "maria@email.com", "11988888888");

        List<Cliente> lista = Arrays.asList(c1, c2);

        when(service.listarTodos()).thenReturn(lista);

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(service).listarTodos();
    }

    @Test
    void testBuscarPorId() throws Exception {
        Cliente cliente = new Cliente(1L, "João Silva", "joao@email.com", "11999999999");

        when(service.buscarPorId(1L)).thenReturn(cliente);

        mockMvc.perform(get("/clientes/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João Silva"));

        verify(service).buscarPorId(1L);
    }

    @Test
    void testAtualizarCliente() throws Exception {
        Cliente clienteAtualizado = new Cliente(1L, "João Silva", "joao@novoemail.com", "11999999999");

        when(service.atualizar(eq(1L), any(Cliente.class))).thenReturn(clienteAtualizado);

        mockMvc.perform(put("/clientes/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("joao@novoemail.com"));

        verify(service).atualizar(eq(1L), any(Cliente.class));
    }

    @Test
    void testDeletarCliente() throws Exception {
        doNothing().when(service).deletar(1L);

        mockMvc.perform(delete("/clientes/{id}", 1))
                .andExpect(status().isNoContent());

        verify(service).deletar(1L);
    }

    @Test
    void testBuscarPorNome() throws Exception {
        Cliente c1 = new Cliente(1L, "João Silva", "joao@email.com", "11999999999");
        Cliente c2 = new Cliente(2L, "Joana Silva", "joana@email.com", "11988888888");
        List<Cliente> lista = Arrays.asList(c1, c2);

        when(service.buscarPorNome("Silva")).thenReturn(lista);

        mockMvc.perform(get("/clientes/buscar")
                        .param("nome", "Silva"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(service).buscarPorNome("Silva");
    }
}
