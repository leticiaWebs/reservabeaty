package com.reservabeaty.reservabeaty.delivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservabeaty.reservabeaty.domain.models.Servico;
import com.reservabeaty.reservabeaty.usecase.service.ServicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ServicoControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ServicoService service;

    @InjectMocks
    private ServicoController controller;

    private ObjectMapper objectMapper;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
     void testCriar() throws Exception {
        Servico servico = new Servico(null, "Corte de Cabelo", "Corte clássico", 50.0, 1L);
        Servico salvo = new Servico(1L, "Corte de Cabelo", "Corte clássico", 50.0, 1L);

        when(service.criar(any(Servico.class))).thenReturn(salvo);

        mockMvc.perform(post("/servicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(servico)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Corte de Cabelo"))
                .andExpect(jsonPath("$.descricao").value("Corte clássico"))
                .andExpect(jsonPath("$.preco").value(50.0))
                .andExpect(jsonPath("$.estabelecimentoId").value(1));

        verify(service, times(1)).criar(any(Servico.class));
    }

    @Test
     void testListarTodos() throws Exception {
        List<Servico> lista = Arrays.asList(
                new Servico(1L, "Corte de Cabelo", "Corte clássico", 50.0, 1L),
                new Servico(2L, "Manicure", "Unhas bem feitas", 30.0, 2L)
        );

        when(service.listarTodos()).thenReturn(lista);

        mockMvc.perform(get("/servicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Corte de Cabelo"))
                .andExpect(jsonPath("$[1].descricao").value("Unhas bem feitas"));

        verify(service, times(1)).listarTodos();
    }

    @Test
     void testBuscarPorId() throws Exception {
        Servico servico = new Servico(1L, "Corte de Cabelo", "Corte clássico", 50.0, 1L);
        when(service.buscarPorId(1L)).thenReturn(servico);

        mockMvc.perform(get("/servicos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Corte de Cabelo"))
                .andExpect(jsonPath("$.preco").value(50.0));

        verify(service, times(1)).buscarPorId(1L);
    }

    @Test
     void testBuscarPorNome() throws Exception {
        List<Servico> lista = Arrays.asList(
                new Servico(1L, "Corte de Cabelo", "Corte clássico", 50.0, 1L)
        );

        when(service.buscarPorNome("Corte de Cabelo")).thenReturn(lista);

        mockMvc.perform(get("/servicos/nome")
                        .param("nome", "Corte de Cabelo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descricao").value("Corte clássico"));

        verify(service, times(1)).buscarPorNome("Corte de Cabelo");
    }

    @Test
     void testBuscarPorFaixaDePreco() throws Exception {
        List<Servico> lista = List.of(
                new Servico(1L, "Corte de Cabelo", "Corte clássico", 50.0, 1L)
        );

        when(service.buscarPorFaixaDePreco(30.0, 60.0)).thenReturn(lista);

        mockMvc.perform(get("/servicos/preco")
                        .param("min", "30")
                        .param("max", "60"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Corte de Cabelo"));

        verify(service, times(1)).buscarPorFaixaDePreco(30.0, 60.0);
    }

    @Test
     void testBuscarPorEstabelecimento() throws Exception {
        List<Servico> lista = Arrays.asList(
                new Servico(1L, "Corte de Cabelo", "Corte clássico", 50.0, 1L)
        );

        when(service.buscarPorEstabelecimento(1L)).thenReturn(lista);

        mockMvc.perform(get("/servicos/estabelecimento/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Corte de Cabelo"));

        verify(service, times(1)).buscarPorEstabelecimento(1L);
    }

    @Test
     void testAtualizar() throws Exception {
        Servico atualizado = new Servico(1L, "Corte de Cabelo Atualizado", "Novo corte", 55.0, 1L);

        when(service.atualizar(eq(1L), any(Servico.class))).thenReturn(atualizado);

        mockMvc.perform(put("/servicos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Corte de Cabelo Atualizado"))
                .andExpect(jsonPath("$.preco").value(55.0));

        verify(service, times(1)).atualizar(eq(1L), any(Servico.class));
    }

    @Test
     void testDeletar() throws Exception {
        doNothing().when(service).deletar(1L);

        mockMvc.perform(delete("/servicos/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deletar(1L);
    }
}
