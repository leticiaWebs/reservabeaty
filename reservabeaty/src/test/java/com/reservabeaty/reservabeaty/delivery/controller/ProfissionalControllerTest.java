package com.reservabeaty.reservabeaty.delivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservabeaty.reservabeaty.domain.models.Profissional;
import com.reservabeaty.reservabeaty.usecase.service.ProfissionalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfissionalControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ProfissionalService service;

    @InjectMocks
    private ProfissionalController controller;

    private ObjectMapper objectMapper;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
     void testCriar() throws Exception {
        Profissional profissional = new Profissional();
        profissional.setId(null);
        profissional.setNome("João");
        profissional.setEspecialidade("Dentista");
        profissional.setTarifa(150.0);

        Profissional salvo = new Profissional();
        salvo.setId(1L);
        salvo.setNome("João");
        salvo.setEspecialidade("Dentista");
        salvo.setTarifa(150.0);

        when(service.criar(any(Profissional.class))).thenReturn(salvo);

        mockMvc.perform(post("/profissionais")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profissional)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.especialidade").value("Dentista"))
                .andExpect(jsonPath("$.tarifa").value(150.0));

        verify(service, times(1)).criar(any(Profissional.class));
    }

    @Test
     void testListarTodos() throws Exception {
        List<Profissional> lista = Arrays.asList(
                new Profissional(1L, "João", "Dentista", 150.0, null),
                new Profissional(2L, "Maria", "Fisioterapeuta", 120.0, null)
        );

        when(service.listarTodos()).thenReturn(lista);

        mockMvc.perform(get("/profissionais"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João"))
                .andExpect(jsonPath("$[1].especialidade").value("Fisioterapeuta"));

        verify(service, times(1)).listarTodos();
    }

    @Test
     void testBuscarPorId() throws Exception {
        Profissional profissional = new Profissional(1L, "João", "Dentista", 150.0, null);
        when(service.buscarPorId(1L)).thenReturn(profissional);

        mockMvc.perform(get("/profissionais/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.tarifa").value(150.0));

        verify(service, times(1)).buscarPorId(1L);
    }

    @Test
     void testBuscarPorEspecialidade() throws Exception {
        List<Profissional> lista = Arrays.asList(
                new Profissional(1L, "João", "Dentista", 150.0, null)
        );

        when(service.buscarPorEspecialidade("Dentista")).thenReturn(lista);

        mockMvc.perform(get("/profissionais/especialidade")
                        .param("especialidade", "Dentista"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João"));

        verify(service, times(1)).buscarPorEspecialidade("Dentista");
    }

    @Test
     void testBuscarPorFaixaDeTarifa() throws Exception {
        List<Profissional> lista = Arrays.asList(
                new Profissional(1L, "João", "Dentista", 150.0, null)
        );

        when(service.buscarPorFaixaDeTarifa(100.0, 200.0)).thenReturn(lista);

        mockMvc.perform(get("/profissionais/tarifa")
                        .param("min", "100")
                        .param("max", "200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João"));

        verify(service, times(1)).buscarPorFaixaDeTarifa(100.0, 200.0);
    }

    @Test
     void testAtualizar() throws Exception {
        Profissional atualizado = new Profissional(1L, "João", "Dentista", 160.0, null);

        when(service.atualizar(eq(1L), any(Profissional.class))).thenReturn(atualizado);

        mockMvc.perform(put("/profissionais/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tarifa").value(160.0));

        verify(service, times(1)).atualizar(eq(1L), any(Profissional.class));
    }

    @Test
     void testDeletar() throws Exception {
        doNothing().when(service).deletar(1L);

        mockMvc.perform(delete("/profissionais/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deletar(1L);
    }
}
