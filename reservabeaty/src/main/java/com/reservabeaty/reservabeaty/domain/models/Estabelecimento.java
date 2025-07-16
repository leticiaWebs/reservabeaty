package com.reservabeaty.reservabeaty.domain.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_estabelecimento")
public class Estabelecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
    @OneToMany
    @JoinColumn(name = "estabelecimentoId")
    private List<Servico> servicos;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "profissionaId")
    private List<Profissional>  profissionais;
    @ElementCollection
    @CollectionTable(name = "tb_horarios_funcionamento", joinColumns = @JoinColumn(name = "estabelecimentId"))
    private List<HorarioFuncionamento> horariosFuncionamento;
    private List<String> fotos;

    public Estabelecimento() {
    }

    public Estabelecimento(Long id, String nome, Endereco endereco, List<Servico> servicos, List<Profissional> profissionais, List<HorarioFuncionamento> horariosFuncionamento, List<String> fotos) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.servicos = servicos;
        this.profissionais = profissionais;
        this.horariosFuncionamento = horariosFuncionamento;
        this.fotos = fotos;
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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    public List<Profissional> getProfissionais() {
        return profissionais;
    }

    public void setProfissionais(List<Profissional> profissionais) {
        this.profissionais = profissionais;
    }

    public List<HorarioFuncionamento> getHorariosFuncionamento() {
        return horariosFuncionamento;
    }

    public void setHorariosFuncionamento(List<HorarioFuncionamento> horariosFuncionamento) {
        this.horariosFuncionamento = horariosFuncionamento;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }
}
