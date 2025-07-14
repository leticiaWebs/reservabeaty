package com.reservabeaty.reservabeaty.domain.repository;

import com.reservabeaty.reservabeaty.domain.models.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {


    // Buscar agendamentos de um cliente
    List<Agendamento> findByClienteId(Long clienteId);

    // Buscar agendamentos de um profissional
    List<Agendamento> findByProfissionalId(Long profissionalId);

    // Buscar agendamentos por data
    List<Agendamento> findByData(LocalDate data);

    // Buscar agendamentos por profissional e data (verificar disponibilidade)
    List<Agendamento> findByProfissionalIdAndData(Long profissionalId, LocalDate data);
}
