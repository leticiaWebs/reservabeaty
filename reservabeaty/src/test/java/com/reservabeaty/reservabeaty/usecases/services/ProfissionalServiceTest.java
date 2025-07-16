package com.reservabeaty.reservabeaty.usecases.services;

import com.reservabeaty.reservabeaty.domain.models.HorarioDisponivel;
import com.reservabeaty.reservabeaty.domain.models.Profissional;
import com.reservabeaty.reservabeaty.domain.repository.ProfissionalRepository;
import com.reservabeaty.reservabeaty.usecase.service.ProfissionalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProfissionalServiceTest {
    @InjectMocks
    private ProfissionalService profissionalService;

    @Mock
    private ProfissionalRepository repository;

    private Profissional profissional;
    private HorarioDisponivel horario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        horario = new HorarioDisponivel(
                1L,
                LocalDate.of(2025, 7, 16),
                LocalTime.of(9, 0),
                LocalTime.of(12, 0)
        );

        profissional = new Profissional();
        profissional.setId(1L);
        profissional.setNome("Leticia");
        profissional.setEspecialidade("Manicure");
        profissional.setTarifa(100.0);
        profissional.setHorariosDisponiveis(Arrays.asList(horario));
    }

    @Test
    void testCriarProfissional() {
        when(repository.save(profissional)).thenReturn(profissional);

        Profissional criado = profissionalService.criar(profissional);

        assertNotNull(criado);
        assertEquals("Leticia", criado.getNome());
        verify(repository).save(profissional);
    }

    @Test
    void testListarTodosProfissionais() {
        List<Profissional> lista = Arrays.asList(profissional);
        when(repository.findAll()).thenReturn(lista);

        List<Profissional> result = profissionalService.listarTodos();

        assertEquals(1, result.size());
        assertEquals(profissional, result.get(0));
        verify(repository).findAll();
    }

    @Test
    void testBuscarPorIdExistente() {
        when(repository.findById(1L)).thenReturn(Optional.of(profissional));

        Profissional encontrado = profissionalService.buscarPorId(1L);

        assertNotNull(encontrado);
        assertEquals(1L, encontrado.getId());
        verify(repository).findById(1L);
    }

    @Test
    void testBuscarPorIdNaoExistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            profissionalService.buscarPorId(1L);
        });

        assertEquals("Profissional não encontrado", exception.getMessage());
        verify(repository).findById(1L);
    }

    @Test
    void testBuscarPorEspecialidade() {
        List<Profissional> lista = Arrays.asList(profissional);
        when(repository.findByEspecialidadeContainingIgnoreCase("Manicure")).thenReturn(lista);

        List<Profissional> result = profissionalService.buscarPorEspecialidade("Manicure");

        assertEquals(1, result.size());
        assertEquals("Manicure", result.get(0).getEspecialidade());
        verify(repository).findByEspecialidadeContainingIgnoreCase("Manicure");
    }

    @Test
    void testBuscarPorFaixaDeTarifa() {
        List<Profissional> lista = Arrays.asList(profissional);
        when(repository.findByTarifaBetween(50.0, 150.0)).thenReturn(lista);

        List<Profissional> result = profissionalService.buscarPorFaixaDeTarifa(50.0, 150.0);

        assertEquals(1, result.size());
        assertTrue(result.get(0).getTarifa() >= 50.0 && result.get(0).getTarifa() <= 150.0);
        verify(repository).findByTarifaBetween(50.0, 150.0);
    }

    @Test
    void testAtualizarProfissionalComSucesso() {
        Profissional atualizado = new Profissional();
        atualizado.setNome("Leticia Oliveira");
        atualizado.setEspecialidade("Depilação");
        atualizado.setTarifa(120.0);
        atualizado.setHorariosDisponiveis(Arrays.asList(horario));

        when(repository.findById(1L)).thenReturn(Optional.of(profissional));
        when(repository.save(any(Profissional.class))).thenAnswer(i -> i.getArgument(0));

        Profissional result = profissionalService.atualizar(1L, atualizado);

        assertEquals("Leticia Oliveira", result.getNome());
        assertEquals("Depilação", result.getEspecialidade());
        assertEquals(120.0, result.getTarifa());
        assertEquals(1, result.getHorariosDisponiveis().size());
        verify(repository).findById(1L);
        verify(repository).save(profissional);
    }

    @Test
    void testDeletarProfissional() {
        doNothing().when(repository).deleteById(1L);

        profissionalService.deletar(1L);

        verify(repository).deleteById(1L);
    }
}
