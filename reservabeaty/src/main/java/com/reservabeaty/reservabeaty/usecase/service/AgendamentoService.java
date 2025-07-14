package com.reservabeaty.reservabeaty.usecase.service;

import com.reservabeaty.reservabeaty.domain.models.Agendamento;
import com.reservabeaty.reservabeaty.domain.models.StatusAgendamento;
import com.reservabeaty.reservabeaty.domain.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoService {
    @Autowired
    private AgendamentoRepository repository;

    public Agendamento criar(Agendamento agendamento) {
        // Verificar se o profissional já possui agendamento neste horário
        List<Agendamento> agendamentosNoDia = repository.findByProfissionalIdAndData(agendamento.getProfissionalId(), agendamento.getData());
        boolean horarioOcupado = agendamentosNoDia.stream()
                .anyMatch(a -> a.getHora().equals(agendamento.getHora()) && !a.getStatus().equals(StatusAgendamento.CANCELADO));

        if (horarioOcupado) {
            throw new RuntimeException("Horário indisponível para agendamento.");
        }

        agendamento.setStatus(StatusAgendamento.PENDENTE);
        return repository.save(agendamento);
    }

    public List<Agendamento> listarTodos() {
        return repository.findAll();
    }

    public List<Agendamento> listarPorCliente(Long clienteId) {
        return repository.findByClienteId(clienteId);
    }

    public List<Agendamento> listarPorProfissional(Long profissionalId) {
        return repository.findByProfissionalId(profissionalId);
    }

    public Agendamento atualizarStatus(Long id, StatusAgendamento status) {
        Agendamento ag = repository.findById(id).orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        ag.setStatus(status);
        return repository.save(ag);
    }

    public void cancelar(Long id) {
        Agendamento ag = repository.findById(id).orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        ag.setStatus(StatusAgendamento.CANCELADO);
        repository.save(ag);
    }
}
