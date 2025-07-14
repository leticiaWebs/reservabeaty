package com.reservabeaty.reservabeaty.domain.repository;

import com.reservabeaty.reservabeaty.domain.models.HorarioDisponivel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HorarioDisponivelRepository extends JpaRepository<HorarioDisponivel, Long> {

    // Buscar horários disponíveis por data
    List<HorarioDisponivel> findByData(LocalDate data);

    // Buscar horários entre duas datas
    List<HorarioDisponivel> findByDataBetween(LocalDate inicio, LocalDate fim);
}
