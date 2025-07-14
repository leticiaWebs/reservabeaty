package com.reservabeaty.reservabeaty.domain.repository;

import com.reservabeaty.reservabeaty.domain.models.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
    // Buscar profissionais por especialidade
    List<Profissional> findByEspecialidadeContainingIgnoreCase(String especialidade);

    // Buscar profissionais por faixa de tarifa
    List<Profissional> findByTarifaBetween(Double min, Double max);
}
