package com.reservabeaty.reservabeaty.delivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservabeaty.reservabeaty.domain.models.Endereco;
import com.reservabeaty.reservabeaty.application.usecase.service.EnderecoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EstabelecimentoControllerTest {
    private MockMvc mockMvc;

    @Mock
    private EnderecoService service;

    @InjectMocks
    private EnderecoController controller;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
     void testCriar() throws Exception {
        Endereco endereco = new Endereco(null, "Rua A", "123", "Bairro X", "Cidade Y", "Estado Z", "12345-678");
        Endereco enderecoSalvo = new Endereco(1L, "Rua A", "123", "Bairro X", "Cidade Y", "Estado Z", "12345-678");

        when(service.criar(any(Endereco.class))).thenReturn(enderecoSalvo);

        mockMvc.perform(post("/enderecos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(endereco)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rua").value("Rua A"));

        verify(service, times(1)).criar(any(Endereco.class));
    }

    @Test
     void testListarTodos() throws Exception {
        List<Endereco> lista = Arrays.asList(
                new Endereco(1L, "Rua A", "123", "Bairro X", "Cidade Y", "Estado Z", "12345-678"),
                new Endereco(2L, "Rua B", "456", "Bairro W", "Cidade Z", "Estado Z", "98765-432")
        );

        when(service.listarTodos()).thenReturn(lista);

        mockMvc.perform(get("/enderecos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rua").value("Rua A"))
                .andExpect(jsonPath("$[1].rua").value("Rua B"));

        verify(service, times(1)).listarTodos();
    }

    @Test
     void testBuscarPorId() throws Exception {
        Endereco endereco = new Endereco(1L, "Rua A", "123", "Bairro X", "Cidade Y", "Estado Z", "12345-678");
        when(service.buscarPorId(1L)).thenReturn(endereco);

        mockMvc.perform(get("/enderecos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rua").value("Rua A"));

        verify(service, times(1)).buscarPorId(1L);
    }

    @Test
     void testAtualizar() throws Exception {
        Endereco atualizado = new Endereco(1L, "Rua Atualizada", "321", "Bairro Z", "Cidade W", "Estado Z", "12345-678");
        when(service.atualizar(eq(1L), any(Endereco.class))).thenReturn(atualizado);

        mockMvc.perform(put("/enderecos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rua").value("Rua Atualizada"));

        verify(service, times(1)).atualizar(eq(1L), any(Endereco.class));
    }

    @Test
     void testDeletar() throws Exception {
        doNothing().when(service).deletar(1L);

        mockMvc.perform(delete("/enderecos/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deletar(1L);
    }

    @Test
     void testBuscarPorCidade() throws Exception {
        List<Endereco> lista = List.of(
                new Endereco(1L, "Rua A", "123", "Bairro X", "Cidade Y", "Estado Z", "12345-678")
        );

        when(service.buscarPorCidade("Cidade Y")).thenReturn(lista);

        mockMvc.perform(get("/enderecos/cidade")
                        .param("cidade", "Cidade Y"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bairro").value("Bairro X"));

        verify(service, times(1)).buscarPorCidade("Cidade Y");
    }

    @Test
     void testBuscarPorBairro() throws Exception {
        List<Endereco> lista = List.of(
                new Endereco(1L, "Rua A", "123", "Bairro X", "Cidade Y", "Estado Z", "12345-678")
        );

        when(service.buscarPorBairro("Bairro X")).thenReturn(lista);

        mockMvc.perform(get("/enderecos/bairro")
                        .param("bairro", "Bairro X"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cidade").value("Cidade Y"));

        verify(service, times(1)).buscarPorBairro("Bairro X");
    }

    @Test
     void testBuscarPorCep() throws Exception {
        Endereco endereco = new Endereco(1L, "Rua A", "123", "Bairro X", "Cidade Y", "Estado Z", "12345-678");
        when(service.buscarPorCep("12345-678")).thenReturn(endereco);

        mockMvc.perform(get("/enderecos/cep/12345-678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bairro").value("Bairro X"));

        verify(service, times(1)).buscarPorCep("12345-678");
    }
}
