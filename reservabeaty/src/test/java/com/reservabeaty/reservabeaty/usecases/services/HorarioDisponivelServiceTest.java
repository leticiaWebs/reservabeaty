package com.reservabeaty.reservabeaty.usecases.services;

import com.reservabeaty.reservabeaty.domain.models.HorarioDisponivel;
import com.reservabeaty.reservabeaty.domain.repository.HorarioDisponivelRepository;

import com.reservabeaty.reservabeaty.usecase.service.HorarioDisponivelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HorarioDisponivelServiceTest {
    @InjectMocks
    private HorarioDisponivelService horarioService;

    @Mock
    private HorarioDisponivelRepository repository;

    private HorarioDisponivel horario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        horario = new HorarioDisponivel(
                1L,
                LocalDate.of(2025, 7, 16),
                LocalTime.of(8, 0),
                LocalTime.of(12, 0)
        );
    }

    @Test
    void testCriarHorario() {
        when(repository.save(horario)).thenReturn(horario);

        HorarioDisponivel criado = horarioService.criar(horario);

        assertNotNull(criado);
        assertEquals(horario.getData(), criado.getData());
        verify(repository).save(horario);
    }

    @Test
    void testListarTodosHorarios() {
        List<HorarioDisponivel> lista = Arrays.asList(horario);
        when(repository.findAll()).thenReturn(lista);

        List<HorarioDisponivel> result = horarioService.listarTodos();

        assertEquals(1, result.size());
        assertEquals(horario, result.get(0));
        verify(repository).findAll();
    }

    @Test
    void testBuscarPorIdExistente() {
        when(repository.findById(1L)).thenReturn(Optional.of(horario));

        HorarioDisponivel encontrado = horarioService.buscarPorId(1L);

        assertNotNull(encontrado);
        assertEquals(1L, encontrado.getId());
        verify(repository).findById(1L);
    }

    @Test
    void testBuscarPorIdNaoExistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            horarioService.buscarPorId(1L);
        });

        assertEquals("Horário não encontrado", exception.getMessage());
        verify(repository).findById(1L);
    }

    @Test
    void testBuscarPorData() {
        List<HorarioDisponivel> lista = Arrays.asList(horario);
        when(repository.findByData(horario.getData())).thenReturn(lista);

        List<HorarioDisponivel> result = horarioService.buscarPorData(horario.getData());

        assertEquals(1, result.size());
        assertEquals(horario, result.get(0));
        verify(repository).findByData(horario.getData());
    }

    @Test
    void testBuscarEntreDatas() {
        LocalDate inicio = LocalDate.of(2025, 7, 1);
        LocalDate fim = LocalDate.of(2025, 7, 31);
        List<HorarioDisponivel> lista = Arrays.asList(horario);
        when(repository.findByDataBetween(inicio, fim)).thenReturn(lista);

        List<HorarioDisponivel> result = horarioService.buscarEntreDatas(inicio, fim);

        assertEquals(1, result.size());
        verify(repository).findByDataBetween(inicio, fim);
    }

    @Test
    void testAtualizarHorarioComSucesso() {
        HorarioDisponivel atualizado = new HorarioDisponivel();
        atualizado.setHoraFim(LocalTime.of(14, 0));

        when(repository.findById(1L)).thenReturn(Optional.of(horario));
        when(repository.save(any(HorarioDisponivel.class))).thenAnswer(i -> i.getArgument(0));

        HorarioDisponivel result = horarioService.atualizar(1L, atualizado);

        assertEquals(LocalTime.of(14, 0), result.getHoraFim());
        verify(repository).findById(1L);
        verify(repository).save(horario);
    }

    @Test
    void testAtualizarHorarioQuandoIdNaoExiste() {
        HorarioDisponivel atualizado = new HorarioDisponivel();
        atualizado.setHoraFim(LocalTime.of(14, 0));

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            horarioService.atualizar(1L, atualizado);
        });

        verify(repository).findById(1L);
        verify(repository, never()).save(any());
    }

    @Test
    void testDeletarHorario() {
        doNothing().when(repository).deleteById(1L);

        horarioService.deletar(1L);

        verify(repository).deleteById(1L);
    }
}
