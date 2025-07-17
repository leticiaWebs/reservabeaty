package com.reservabeaty.reservabeaty.application.usecase.service;

import com.reservabeaty.reservabeaty.domain.models.Servico;
import com.reservabeaty.reservabeaty.domain.repository.ServicoRepository;
import com.reservabeaty.reservabeaty.application.usecase.service.ServicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicoServiceTest {

    @InjectMocks
    private ServicoService servicoService;

    @Mock
    private ServicoRepository repository;

    private Servico servico;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        servico = new Servico(
                1L,
                "Corte de cabelo",
                "Corte feminino e masculino",
                50.0,
                10L
        );
    }

    @Test
    void testCriarServico() {
        when(repository.save(servico)).thenReturn(servico);

        Servico criado = servicoService.criar(servico);

        assertNotNull(criado);
        assertEquals("Corte de cabelo", criado.getNome());
        verify(repository).save(servico);
    }

    @Test
    void testListarTodosServicos() {
        List<Servico> lista = Arrays.asList(servico);
        when(repository.findAll()).thenReturn(lista);

        List<Servico> result = servicoService.listarTodos();

        assertEquals(1, result.size());
        assertEquals(servico, result.get(0));
        verify(repository).findAll();
    }

    @Test
    void testBuscarPorIdExistente() {
        when(repository.findById(1L)).thenReturn(Optional.of(servico));

        Servico encontrado = servicoService.buscarPorId(1L);

        assertNotNull(encontrado);
        assertEquals(1L, encontrado.getId());
        verify(repository).findById(1L);
    }

    @Test
    void testBuscarPorIdNaoExistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicoService.buscarPorId(1L);
        });

        assertEquals("Serviço não encontrado", exception.getMessage());
        verify(repository).findById(1L);
    }

    @Test
    void testBuscarPorNome() {
        List<Servico> lista = Arrays.asList(servico);
        when(repository.findByNomeContainingIgnoreCase("corte")).thenReturn(lista);

        List<Servico> result = servicoService.buscarPorNome("corte");

        assertEquals(1, result.size());
        assertEquals("Corte de cabelo", result.get(0).getNome());
        verify(repository).findByNomeContainingIgnoreCase("corte");
    }

    @Test
    void testBuscarPorFaixaDePreco() {
        List<Servico> lista = Arrays.asList(servico);
        when(repository.findByPrecoBetween(40.0, 60.0)).thenReturn(lista);

        List<Servico> result = servicoService.buscarPorFaixaDePreco(40.0, 60.0);

        assertEquals(1, result.size());
        assertTrue(result.get(0).getPreco() >= 40.0 && result.get(0).getPreco() <= 60.0);
        verify(repository).findByPrecoBetween(40.0, 60.0);
    }

    @Test
    void testBuscarPorEstabelecimento() {
        List<Servico> lista = Arrays.asList(servico);
        when(repository.findByEstabelecimentoId(10L)).thenReturn(lista);

        List<Servico> result = servicoService.buscarPorEstabelecimento(10L);

        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).getEstabelecimentoId());
        verify(repository).findByEstabelecimentoId(10L);
    }

    @Test
    void testAtualizarServicoComSucesso() {
        Servico atualizado = new Servico();
        atualizado.setNome("Corte Masculino");
        atualizado.setDescricao("Corte masculino moderno");
        atualizado.setPreco(55.0);
        atualizado.setEstabelecimentoId(10L);

        when(repository.findById(1L)).thenReturn(Optional.of(servico));
        when(repository.save(any(Servico.class))).thenAnswer(i -> i.getArgument(0));

        Servico result = servicoService.atualizar(1L, atualizado);

        assertEquals("Corte Masculino", result.getNome());
        assertEquals("Corte masculino moderno", result.getDescricao());
        assertEquals(55.0, result.getPreco());
        assertEquals(10L, result.getEstabelecimentoId());
        verify(repository).findById(1L);
        verify(repository).save(servico);
    }

    @Test
    void testDeletarServico() {
        doNothing().when(repository).deleteById(1L);

        servicoService.deletar(1L);

        verify(repository).deleteById(1L);
    }
}
