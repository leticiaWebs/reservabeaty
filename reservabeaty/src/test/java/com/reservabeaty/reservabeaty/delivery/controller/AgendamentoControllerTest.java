package com.reservabeaty.reservabeaty.delivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservabeaty.reservabeaty.domain.models.Agendamento;
import com.reservabeaty.reservabeaty.domain.models.StatusAgendamento;
import com.reservabeaty.reservabeaty.application.usecase.service.AgendamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AgendamentoControllerTest {


    private MockMvc mockMvc;

    @Mock
    private AgendamentoService service;

    @InjectMocks
    private AgendamentoController controller;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
     void testListarTodos() throws Exception {
        Agendamento a1 = new Agendamento(1L, 1L, 2L, 3L, LocalDate.of(2025, 7, 20), LocalTime.of(14, 30), StatusAgendamento.PENDENTE);
        Agendamento a2 = new Agendamento(2L, 4L, 5L, 6L, LocalDate.of(2025, 7, 21), LocalTime.of(15, 0), StatusAgendamento.FINALIZADO);
        List<Agendamento> lista = Arrays.asList(a1, a2);

        when(service.listarTodos()).thenReturn(lista);

        mockMvc.perform(get("/agendamentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(service).listarTodos();
    }

    @Test
    void testListarPorCliente() throws Exception {
        Long clienteId = 1L;
        Agendamento a1 = new Agendamento(1L, clienteId, 2L, 3L, LocalDate.of(2025, 7, 20), LocalTime.of(14, 30), StatusAgendamento.PENDENTE);
        List<Agendamento> lista = Arrays.asList(a1);

        when(service.listarPorCliente(clienteId)).thenReturn(lista);

        mockMvc.perform(get("/agendamentos/cliente/{clienteId}", clienteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(service).listarPorCliente(clienteId);
    }

    @Test
    void testListarPorProfissional() throws Exception {
        Long profissionalId = 2L;
        Agendamento a1 = new Agendamento(1L, 1L, profissionalId, 3L, LocalDate.of(2025, 7, 20), LocalTime.of(14, 30), StatusAgendamento.PENDENTE);
        List<Agendamento> lista = Arrays.asList(a1);

        when(service.listarPorProfissional(profissionalId)).thenReturn(lista);

        mockMvc.perform(get("/agendamentos/profissional/{profissionalId}", profissionalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(service).listarPorProfissional(profissionalId);
    }

    @Test
    void testAtualizarStatus() throws Exception {
        Long id = 1L;
        StatusAgendamento novoStatus = StatusAgendamento.FINALIZADO;
        Agendamento agendamento = new Agendamento(id, 1L, 2L, 3L, LocalDate.of(2025, 7, 20), LocalTime.of(14, 30), novoStatus);

        when(service.atualizarStatus(eq(id), eq(novoStatus))).thenReturn(agendamento);

        mockMvc.perform(put("/agendamentos/{id}/status", id)
                        .param("status", novoStatus.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(novoStatus.name()));

        verify(service).atualizarStatus(id, novoStatus);
    }

    @Test
    void testCancelar() throws Exception {
        Long id = 1L;
        doNothing().when(service).cancelar(id);

        mockMvc.perform(delete("/agendamentos/{id}", id))
                .andExpect(status().isNoContent());

        verify(service).cancelar(id);
    }
}
