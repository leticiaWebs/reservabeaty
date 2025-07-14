package com.reservabeaty.reservabeaty.usecase.service;


import com.reservabeaty.reservabeaty.domain.models.HorarioDisponivel;
import com.reservabeaty.reservabeaty.domain.repository.HorarioDisponivelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HorarioDisponivelService {

    @Autowired
    private HorarioDisponivelRepository repository;

    public HorarioDisponivel criar(HorarioDisponivel horario) {
        return repository.save(horario);
    }

    public List<HorarioDisponivel> listarTodos() {
        return repository.findAll();
    }

    public HorarioDisponivel buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horário não encontrado"));
    }

    public List<HorarioDisponivel> buscarPorData(LocalDate data) {
        return repository.findByData(data);
    }

    public List<HorarioDisponivel> buscarEntreDatas(LocalDate inicio, LocalDate fim) {
        return repository.findByDataBetween(inicio, fim);
    }

    public HorarioDisponivel atualizar(Long id, HorarioDisponivel atualizado) {
        HorarioDisponivel horario = repository.findById(id).orElseThrow();
        horario.setHoraFim(atualizado.getHoraFim()); // erro se atualizado não tiver getHoraFim()
        return repository.save(horario);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
