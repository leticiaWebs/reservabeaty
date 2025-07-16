package com.reservabeaty.reservabeaty.usecases.services;

import com.reservabeaty.reservabeaty.domain.models.Avaliacao;
import com.reservabeaty.reservabeaty.domain.models.Cliente;
import com.reservabeaty.reservabeaty.domain.models.Estabelecimento;
import com.reservabeaty.reservabeaty.domain.models.Profissional;
import com.reservabeaty.reservabeaty.domain.repository.AvaliacaoRepository;
import com.reservabeaty.reservabeaty.usecase.service.AvaliacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class AvaliacaoServiceTest {
    @Mock
    private AvaliacaoRepository repository;

    @InjectMocks
    private AvaliacaoService service;

    private Avaliacao avaliacao;
    private Avaliacao avaliacao2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Criando objetos de exemplo
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        Profissional profissional = new Profissional();
        profissional.setId(2L);
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId(3L);

        avaliacao = new Avaliacao(1L, "Ótimo serviço", 5, cliente, profissional, estabelecimento);
        avaliacao2 = new Avaliacao(2L, "Ruim", 2, cliente, profissional, estabelecimento);
    }

    @Test
    void testSalvar() {
        when(repository.save(any(Avaliacao.class))).thenReturn(avaliacao);
        Avaliacao resultado = service.salvar(avaliacao);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repository, times(1)).save(any(Avaliacao.class));
    }

    @Test
    void testListarPorEstabelecimento() {
        Long estabelecimentoId = 3L;
        when(repository.findByEstabelecimento_Id(estabelecimentoId))
                .thenReturn(Arrays.asList(avaliacao, avaliacao2));
        List<Avaliacao> resultado = service.listarPorEstabelecimento(estabelecimentoId);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findByEstabelecimento_Id(estabelecimentoId);
    }

    @Test
    void testListarPorProfissional() {
        Long profissionalId = 2L;
        when(repository.findByProfissional_Id(profissionalId))
                .thenReturn(Arrays.asList(avaliacao));
        List<Avaliacao> resultado = service.listarPorProfissional(profissionalId);
        assertEquals(1, resultado.size());
        verify(repository, times(1)).findByProfissional_Id(profissionalId);
    }

    @Test
    void testListarPorCliente() {
        Long clienteId = 1L;
        when(repository.findByCliente_Id(clienteId))
                .thenReturn(Arrays.asList(avaliacao, avaliacao2));
        List<Avaliacao> resultado = service.listarPorCliente(clienteId);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findByCliente_Id(clienteId);
    }
}
