package com.reservabeaty.reservabeaty.delivery.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import com.reservabeaty.reservabeaty.domain.models.Avaliacao;
import com.reservabeaty.reservabeaty.domain.models.Cliente;
import com.reservabeaty.reservabeaty.domain.models.Estabelecimento;
import com.reservabeaty.reservabeaty.domain.models.Profissional;
import com.reservabeaty.reservabeaty.usecase.service.AvaliacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;


class AvaliacaoControllerTest {


    private MockMvc mockMvc;

    @Mock
    private AvaliacaoService service;

    @InjectMocks
    private AvaliacaoController controller;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testCriarAvaliacao() throws Exception {
        // Criando objeto Avaliacao para retorno do mock
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        Profissional profissional = new Profissional();
        profissional.setId(2L);
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId(3L);

        Avaliacao avaliacao = new Avaliacao(null, "Ótimo serviço", 5, cliente, profissional, estabelecimento);
        Avaliacao avaliacaoRetorno = new Avaliacao(10L, "Ótimo serviço", 5, cliente, profissional, estabelecimento);

        when(service.salvar(any(Avaliacao.class))).thenReturn(avaliacaoRetorno);

        mockMvc.perform(post("/avaliacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(avaliacao)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.comentario").value("Ótimo serviço"));

        verify(service).salvar(any(Avaliacao.class));
    }

    @Test
    void testListarPorEstabelecimento() throws Exception {
        Long estabelecimentoId = 3L;

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        Profissional profissional = new Profissional();
        profissional.setId(2L);
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId(estabelecimentoId);

        Avaliacao a1 = new Avaliacao(1L, "Bom atendimento", 4, cliente, profissional, estabelecimento);
        Avaliacao a2 = new Avaliacao(2L, "Muito bom", 5, cliente, profissional, estabelecimento);

        List<Avaliacao> lista = Arrays.asList(a1, a2);

        when(service.listarPorEstabelecimento(estabelecimentoId)).thenReturn(lista);

        mockMvc.perform(get("/avaliacoes/estabelecimento/{id}", estabelecimentoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(service).listarPorEstabelecimento(estabelecimentoId);
    }

    @Test
    void testListarPorProfissional() throws Exception {
        Long profissionalId = 2L;

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        Profissional profissional = new Profissional();
        profissional.setId(profissionalId);
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId(3L);

        Avaliacao a1 = new Avaliacao(1L, "Atencioso", 4, cliente, profissional, estabelecimento);
        List<Avaliacao> lista = Arrays.asList(a1);

        when(service.listarPorProfissional(profissionalId)).thenReturn(lista);

        mockMvc.perform(get("/avaliacoes/profissional/{id}", profissionalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(service).listarPorProfissional(profissionalId);
    }
}
