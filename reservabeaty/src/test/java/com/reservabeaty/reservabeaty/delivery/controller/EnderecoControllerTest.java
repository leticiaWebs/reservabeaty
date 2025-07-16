package com.reservabeaty.reservabeaty.delivery.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import com.reservabeaty.reservabeaty.domain.models.Endereco;
import com.reservabeaty.reservabeaty.usecase.service.EnderecoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

class EnderecoControllerTest {


    private MockMvc mockMvc;

    @Mock
    private EnderecoService service;

    @InjectMocks
    private EnderecoController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testCriarEndereco() throws Exception {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setRua("Rua A");
        endereco.setNumero("123");
        endereco.setBairro("Centro");
        endereco.setCidade("Cidade X");
        endereco.setEstado("Estado Y");
        endereco.setCep("12345-678");

        Endereco enderecoRetorno = new Endereco();
        enderecoRetorno.setId(1L);
        enderecoRetorno.setRua("Rua A");
        enderecoRetorno.setNumero("123");
        enderecoRetorno.setBairro("Centro");
        enderecoRetorno.setCidade("Cidade X");
        enderecoRetorno.setEstado("Estado Y");
        enderecoRetorno.setCep("12345-678");

        when(service.criar(any(Endereco.class))).thenReturn(enderecoRetorno);

        mockMvc.perform(post("/enderecos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(endereco)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.bairro").value("Centro"));

        verify(service).criar(any(Endereco.class));
    }

    @Test
    void testListarTodos() throws Exception {
        Endereco e1 = new Endereco(1L, "Rua A", "123", "Centro", "Cidade X", "Estado Y", "12345-678");
        Endereco e2 = new Endereco(2L, "Rua B", "456", "Bairro Z", "Cidade Y", "Estado Z", "98765-432");
        List<Endereco> lista = Arrays.asList(e1, e2);

        when(service.listarTodos()).thenReturn(lista);

        mockMvc.perform(get("/enderecos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(service).listarTodos();
    }

    @Test
    void testBuscarPorId() throws Exception {
        Endereco endereco = new Endereco(1L, "Rua A", "123", "Centro", "Cidade X", "Estado Y", "12345-678");

        when(service.buscarPorId(1L)).thenReturn(endereco);

        mockMvc.perform(get("/enderecos/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.bairro").value("Centro"));

        verify(service).buscarPorId(1L);
    }

    @Test
    void testAtualizarEndereco() throws Exception {
        Endereco enderecoAtualizado = new Endereco(1L, "Rua A", "124", "Centro", "Cidade X", "Estado Y", "12345-678");

        when(service.atualizar(eq(1L), any(Endereco.class))).thenReturn(enderecoAtualizado);

        mockMvc.perform(put("/enderecos/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enderecoAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numero").value("124"));

        verify(service).atualizar(eq(1L), any(Endereco.class));
    }

    @Test
    void testDeletarEndereco() throws Exception {
        doNothing().when(service).deletar(1L);

        mockMvc.perform(delete("/enderecos/{id}", 1))
                .andExpect(status().isNoContent());

        verify(service).deletar(1L);
    }

    @Test
    void testBuscarPorCidade() throws Exception {
        Endereco e1 = new Endereco(1L, "Rua A", "123", "Centro", "Cidade X", "Estado Y", "12345-678");
        Endereco e2 = new Endereco(2L, "Rua B", "456", "Bairro Z", "Cidade X", "Estado Z", "98765-432");
        List<Endereco> lista = Arrays.asList(e1, e2);

        when(service.buscarPorCidade("Cidade X")).thenReturn(lista);

        mockMvc.perform(get("/enderecos/cidade")
                        .param("cidade", "Cidade X"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(service).buscarPorCidade("Cidade X");
    }

    @Test
    void testBuscarPorBairro() throws Exception {
        Endereco e1 = new Endereco(1L, "Rua A", "123", "Centro", "Cidade X", "Estado Y", "12345-678");
        Endereco e2 = new Endereco(2L, "Rua C", "789", "Centro", "Cidade Z", "Estado Y", "54321-987");
        List<Endereco> lista = Arrays.asList(e1, e2);

        when(service.buscarPorBairro("Centro")).thenReturn(lista);

        mockMvc.perform(get("/enderecos/bairro")
                        .param("bairro", "Centro"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(service).buscarPorBairro("Centro");
    }

    @Test
    void testBuscarPorCep() throws Exception {
        Endereco endereco = new Endereco(1L, "Rua A", "123", "Centro", "Cidade X", "Estado Y", "12345-678");

        when(service.buscarPorCep("12345-678")).thenReturn(endereco);

        mockMvc.perform(get("/enderecos/cep/{cep}", "12345-678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cep").value("12345-678"));

        verify(service).buscarPorCep("12345-678");
    }
}
