package com.reservabeaty.reservabeaty.usecases.services;

import com.reservabeaty.reservabeaty.domain.models.Estabelecimento;
import com.reservabeaty.reservabeaty.domain.repository.EstabelecimentoRepository;
import com.reservabeaty.reservabeaty.application.usecase.service.EstabelecimentoService;
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

class EstabelecimentoServiceTest {
    @Mock
    private EstabelecimentoRepository repository;

    @InjectMocks
    private EstabelecimentoService service;

    private Estabelecimento estabelecimento;
    private Estabelecimento atualizacao;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
        estabelecimento = new Estabelecimento();
        estabelecimento.setId(1L);
        estabelecimento.setNome("Estabelecimento A");
        // Você pode inicializar os demais atributos conforme necessário

        // Estabelecimento para atualização
        atualizacao = new Estabelecimento();
        atualizacao.setNome("Estabelecimento Atualizado");
        // Outros atributos também podem ser definidos
    }

    @Test
     void testCriar() {
        when(repository.save(any(Estabelecimento.class))).thenReturn(estabelecimento);
        Estabelecimento resultado = service.criar(estabelecimento);
        assertNotNull(resultado);
        assertEquals("Estabelecimento A", resultado.getNome());
        verify(repository).save(estabelecimento);
    }

    @Test
     void testListarTodos() {
        when(repository.findAll()).thenReturn(Arrays.asList(estabelecimento));
        List<Estabelecimento> lista = service.listarTodos();
        assertEquals(1, lista.size());
        assertEquals("Estabelecimento A", lista.get(0).getNome());
        verify(repository).findAll();
    }

    @Test
     void testBuscarPorId_Sucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(estabelecimento));
        Estabelecimento resultado = service.buscarPorId(1L);
        assertNotNull(resultado);
        assertEquals("Estabelecimento A", resultado.getNome());
        verify(repository).findById(1L);
    }

    @Test
     void testBuscarPorId_NaoEncontrado() {
        when(repository.findById(2L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.buscarPorId(2L);
        });
        assertEquals("Estabelecimento não encontrado", exception.getMessage());
    }

    @Test
     void testAtualizar() {
        when(repository.findById(1L)).thenReturn(Optional.of(estabelecimento));
        when(repository.save(any(Estabelecimento.class))).thenAnswer(i -> i.getArguments()[0]);

        Estabelecimento resultado = service.atualizar(1L, atualizacao);
        assertNotNull(resultado);
        assertEquals("Estabelecimento Atualizado", resultado.getNome());
        verify(repository).save(estabelecimento);
    }

    @Test
     void testDeletar() {
        doNothing().when(repository).deleteById(1L);
        service.deletar(1L);
        verify(repository).deleteById(1L);
    }

    @Test
     void testBuscarPorNome() {
        List<Estabelecimento> lista = Arrays.asList(estabelecimento);
        when(repository.findByNomeContainingIgnoreCase("Estabelecimento")).thenReturn(lista);
        List<Estabelecimento> resultado = service.buscarPorNome("Estabelecimento");
        assertEquals(1, resultado.size());
        verify(repository).findByNomeContainingIgnoreCase("Estabelecimento");
    }

    @Test
     void testBuscarPorCidade() {
        List<Estabelecimento> lista = Arrays.asList(estabelecimento);
        when(repository.findByEnderecoCidadeContainingIgnoreCase("CidadeX")).thenReturn(lista);
        List<Estabelecimento> resultado = service.buscarPorCidade("CidadeX");
        assertEquals(1, resultado.size());
        verify(repository).findByEnderecoCidadeContainingIgnoreCase("CidadeX");
    }

    @Test
     void testBuscarPorBairro() {
        List<Estabelecimento> lista = Arrays.asList(estabelecimento);
        when(repository.findByEnderecoBairroContainingIgnoreCase("BairroY")).thenReturn(lista);
        List<Estabelecimento> resultado = service.buscarPorBairro("BairroY");
        assertEquals(1, resultado.size());
        verify(repository).findByEnderecoBairroContainingIgnoreCase("BairroY");
    }
}
