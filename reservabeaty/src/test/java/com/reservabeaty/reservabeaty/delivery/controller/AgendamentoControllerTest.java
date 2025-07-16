package com.reservabeaty.reservabeaty.delivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservabeaty.reservabeaty.domain.models.Agendamento;
import com.reservabeaty.reservabeaty.domain.models.StatusAgendamento;
import com.reservabeaty.reservabeaty.usecase.service.AgendamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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
    void testCriarAgendamento() throws Exception {
        Agendamento agendamento = new Agendamento();
        agendamento.setId(1L);
        agendamento.setClienteId(100L);
        agendamento.setProfissionalId(200L);
        agendamento.setServicoId(300L);
        agendamento.setData(LocalDate.of(2025, 7, 20));
        agendamento.setHora(LocalTime.of(14, 30));
        agendamento.setStatus(StatusAgendamento.PENDENTE);

        when(service.criar(any(Agendamento.class))).thenReturn(agendamento);

        mockMvc.perform(post("/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(objectMapper.writeValueAsString(agendamento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.clienteId").value(100));

        verify(service).criar(any(Agendamento.class));
    }

    @Test
    void testListarTodos() throws Exception {
        List<Agendamento> lista = Arrays.asList(
                new Agendamento(1L, 100L, 200L, 300L, LocalDate.of(2025, 7, 20), LocalTime.of(14, 30), StatusAgendamento.PENDENTE),
                new Agendamento(2L, 101L, 201L, 301L, LocalDate.of(2025, 7, 21), LocalTime.of(15, 0), StatusAgendamento.FINALIZADO)
        );

        when(service.listarTodos()).thenReturn(lista);

        mockMvc.perform(get("/agendamentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(service).listarTodos();
    }

    @Test
    void testListarPorCliente() throws Exception {
        Long clienteId = 100L;
        List<Agendamento> lista = Arrays.asList(
                new Agendamento(1L, clienteId, 200L, 300L, LocalDate.of(2025, 7, 20), LocalTime.of(14, 30), StatusAgendamento.PENDENTE)
        );

        when(service.listarPorCliente(clienteId)).thenReturn(lista);

        mockMvc.perform(get("/agendamentos/cliente/{clienteId}", clienteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(service).listarPorCliente(clienteId);
    }

    @Test
    void testListarPorProfissional() throws Exception {
        Long profissionalId = 200L;
        List<Agendamento> lista = Arrays.asList(
                new Agendamento(1L, 100L, profissionalId, 300L, LocalDate.of(2025, 7, 20), LocalTime.of(14, 30), StatusAgendamento.PENDENTE)
        );

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

        Agendamento agendamento = new Agendamento();
        agendamento.setId(id);
        agendamento.setStatus(novoStatus);

        when(service.atualizarStatus((id), (novoStatus))).thenReturn(agendamento);

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
