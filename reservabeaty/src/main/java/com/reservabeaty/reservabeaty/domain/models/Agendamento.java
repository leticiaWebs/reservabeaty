package com.reservabeaty.reservabeaty.domain.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name = "tb_agendamento")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clienteId;
    private Long profissionalId;
    private Long servicoId;
    private LocalDate data;
    private LocalTime hora;
    private StatusAgendamento status;


    public Agendamento() {
    }

    public Agendamento(Long id, Long clienteId, Long profissionalId, Long servicoId, LocalDate data, LocalTime hora, StatusAgendamento status) {
        this.id = id;
        this.clienteId = clienteId;
        this.profissionalId = profissionalId;
        this.servicoId = servicoId;
        this.data = data;
        this.hora = hora;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(Long profissionalId) {
        this.profissionalId = profissionalId;
    }

    public Long getServicoId() {
        return servicoId;
    }

    public void setServicoId(Long servicoId) {
        this.servicoId = servicoId;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }
}




