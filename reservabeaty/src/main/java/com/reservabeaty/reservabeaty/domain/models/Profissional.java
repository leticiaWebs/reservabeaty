package com.reservabeaty.reservabeaty.domain.models;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "tb_profissional")
public class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String especialidade;
    private Double tarifa;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "profissionalId")
    private List<HorarioDisponivel> horariosDisponiveis;

    public Profissional() {
    }

    public Profissional(Long id, String nome, String especialidade, Double tarifa, List<HorarioDisponivel> horariosDisponiveis) {
        this.id = id;
        this.nome = nome;
        this.especialidade = especialidade;
        this.tarifa = tarifa;
        this.horariosDisponiveis = horariosDisponiveis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Double getTarifa() {
        return tarifa;
    }

    public void setTarifa(Double tarifa) {
        this.tarifa = tarifa;
    }

    public List<HorarioDisponivel> getHorariosDisponiveis() {
        return horariosDisponiveis;
    }

    public void setHorariosDisponiveis(List<HorarioDisponivel> horariosDisponiveis) {
        this.horariosDisponiveis = horariosDisponiveis;
    }
}
