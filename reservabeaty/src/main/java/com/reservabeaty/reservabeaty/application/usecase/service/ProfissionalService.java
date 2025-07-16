package com.reservabeaty.reservabeaty.application.usecase.service;

import com.reservabeaty.reservabeaty.domain.models.Profissional;
import com.reservabeaty.reservabeaty.domain.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository repository;

    public Profissional criar(Profissional profissional) {
        return repository.save(profissional);
    }

    public List<Profissional> listarTodos() {
        return repository.findAll();
    }

    public Profissional buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
    }

    public List<Profissional> buscarPorEspecialidade(String especialidade) {
        return repository.findByEspecialidadeContainingIgnoreCase(especialidade);
    }

    public List<Profissional> buscarPorFaixaDeTarifa(Double min, Double max) {
        return repository.findByTarifaBetween(min, max);
    }

    public Profissional atualizar(Long id, Profissional atualizado) {
        Profissional profissional = buscarPorId(id);
        profissional.setNome(atualizado.getNome());
        profissional.setEspecialidade(atualizado.getEspecialidade());
        profissional.setTarifa(atualizado.getTarifa());
        profissional.setHorariosDisponiveis(atualizado.getHorariosDisponiveis());
        return repository.save(profissional);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
