package com.reservabeaty.reservabeaty.delivery.controller;

import com.reservabeaty.reservabeaty.domain.models.HorarioDisponivel;
import com.reservabeaty.reservabeaty.application.usecase.service.HorarioDisponivelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


class HorarioDisponivelControllerTest {

    @Mock
    private HorarioDisponivelService service;

    @InjectMocks
    private HorarioDisponivelController controller;

    private HorarioDisponivel horario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        horario = new HorarioDisponivel(1L, LocalDate.of(2025, 7, 20), LocalTime.of(9, 0), LocalTime.of(10, 0));
    }

    @Test
    void testCriar() {
        when(service.criar(any(HorarioDisponivel.class))).thenReturn(horario);

        ResponseEntity<HorarioDisponivel> response = controller.criar(horario);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(horario, response.getBody());
        verify(service).criar(any(HorarioDisponivel.class));
    }

    @Test
    void testListarTodos() {
        List<HorarioDisponivel> lista = Arrays.asList(horario);
        when(service.listarTodos()).thenReturn(lista);

        ResponseEntity<List<HorarioDisponivel>> response = controller.listarTodos();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(lista, response.getBody());
        verify(service).listarTodos();
    }

    @Test
    void testBuscarPorId() {
        when(service.buscarPorId(anyLong())).thenReturn(horario);

        ResponseEntity<HorarioDisponivel> response = controller.buscarPorId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(horario, response.getBody());
        verify(service).buscarPorId(1L);
    }

    @Test
    void testBuscarPorData() {
        List<HorarioDisponivel> lista = Collections.singletonList(horario);
        when(service.buscarPorData(any(LocalDate.class))).thenReturn(lista);

        ResponseEntity<List<HorarioDisponivel>> response = controller.buscarPorData(LocalDate.of(2025, 7, 20));

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(lista, response.getBody());
        verify(service).buscarPorData(LocalDate.of(2025, 7, 20));
    }

    @Test
    void testBuscarEntreDatas() {
        List<HorarioDisponivel> lista = Arrays.asList(horario);
        when(service.buscarEntreDatas(any(LocalDate.class), any(LocalDate.class))).thenReturn(lista);

        ResponseEntity<List<HorarioDisponivel>> response = controller.buscarEntreDatas(LocalDate.of(2025, 7, 20), LocalDate.of(2025, 7, 25));

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(lista, response.getBody());
        verify(service).buscarEntreDatas(LocalDate.of(2025, 7, 20), LocalDate.of(2025, 7, 25));
    }

    @Test
    void testAtualizar() {
        HorarioDisponivel atualizado = new HorarioDisponivel(1L, LocalDate.of(2025, 7, 20), LocalTime.of(10, 0), LocalTime.of(11, 0));
        when(service.atualizar(anyLong(), any(HorarioDisponivel.class))).thenReturn(atualizado);

        ResponseEntity<HorarioDisponivel> response = controller.atualizar(1L, atualizado);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(atualizado, response.getBody());
        verify(service).atualizar(1L, atualizado);
    }

    @Test
    void testDeletar() {
        doNothing().when(service).deletar(anyLong());

        ResponseEntity<Void> response = controller.deletar(1L);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(service).deletar(1L);
    }
}
