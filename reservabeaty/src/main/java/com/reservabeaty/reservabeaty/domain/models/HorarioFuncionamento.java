package com.reservabeaty.reservabeaty.domain.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;


@Embeddable
public class HorarioFuncionamento {
    private String diaSemana;
    private String horarioAbertura;
    private String horarioFechamento;

}
