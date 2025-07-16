package com.reservabeaty.reservabeaty.usecases.services;

import com.reservabeaty.reservabeaty.domain.models.Agendamento;
import com.reservabeaty.reservabeaty.domain.models.StatusAgendamento;
import com.reservabeaty.reservabeaty.domain.repository.AgendamentoRepository;
import com.reservabeaty.reservabeaty.application.usecase.service.AgendamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AgendamentoServiceTest {
    @Mock
    private AgendamentoRepository repository;

    @InjectMocks
    private AgendamentoService service;

    private Agendamento agendamento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        agendamento = new Agendamento();
        agendamento.setId(1L);
        agendamento.setClienteId(100L);
        agendamento.setProfissionalId(200L);
        agendamento.setServicoId(300L);
        agendamento.setData(LocalDate.of(2025, 7, 20));
        agendamento.setHora(LocalTime.of(10, 0));
        agendamento.setStatus(StatusAgendamento.PENDENTE);
    }

    @Test
    void testCriar_Sucesso() {
        // Simula que não há agendamento no mesmo horário
        when(repository.findByProfissionalIdAndData(anyLong(), any(LocalDate.class)))
                .thenReturn(Arrays.asList());

        when(repository.save(any(Agendamento.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Agendamento resultado = service.criar(agendamento);

        assertNotNull(resultado);
        assertEquals(StatusAgendamento.PENDENTE, resultado.getStatus());
        verify(repository).save(any(Agendamento.class));
    }

    @Test
    void testCriar_HorarioOcupado() {
        // Simula agendamento existente no mesmo horário e profissional
        Agendamento existente = new Agendamento();
        existente.setHora(LocalTime.of(10, 0));
        existente.setStatus(StatusAgendamento.PENDENTE);
        when(repository.findByProfissionalIdAndData(anyLong(), any(LocalDate.class)))
                .thenReturn(Arrays.asList(existente));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.criar(agendamento);
        });

        assertEquals("Horário indisponível para agendamento.", exception.getMessage());
        verify(repository, never()).save(any(Agendamento.class));
    }

    @Test
    void testListarTodos() {
        List<Agendamento> lista = Arrays.asList(agendamento);
        when(repository.findAll()).thenReturn(lista);

        List<Agendamento> resultado = service.listarTodos();

        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void testListarPorCliente() {
        List<Agendamento> lista = Arrays.asList(agendamento);
        when(repository.findByClienteId(anyLong())).thenReturn(lista);

        List<Agendamento> resultado = service.listarPorCliente(100L);

        assertEquals(1, resultado.size());
        verify(repository).findByClienteId(100L);
    }

    @Test
    void testListarPorProfissional() {
        List<Agendamento> lista = Arrays.asList(agendamento);
        when(repository.findByProfissionalId(anyLong())).thenReturn(lista);

        List<Agendamento> resultado = service.listarPorProfissional(200L);

        assertEquals(1, resultado.size());
        verify(repository).findByProfissionalId(200L);
    }

    @Test
    void testAtualizarStatus_Sucesso() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(agendamento));
        when(repository.save(any(Agendamento.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Agendamento resultado = service.atualizarStatus(1L, StatusAgendamento.CONFIRMADO);

        assertEquals(StatusAgendamento.CONFIRMADO, resultado.getStatus());
        verify(repository).save(agendamento);
    }

    @Test
    void testAtualizarStatus_AgendamentoNaoEncontrado() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.atualizarStatus(999L, StatusAgendamento.CONFIRMADO);
        });

        assertEquals("Agendamento não encontrado", exception.getMessage());
    }

    @Test
    void testCancelar_Sucesso() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(agendamento));
        when(repository.save(any(Agendamento.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        service.cancelar(1L);

        assertEquals(StatusAgendamento.CANCELADO, agendamento.getStatus());
        verify(repository).save(agendamento);
    }

    @Test
    void testCancelar_AgendamentoNaoEncontrado() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.cancelar(999L);
        });

        assertEquals("Agendamento não encontrado", exception.getMessage());
    }
}
