package com.reservabeaty.reservabeaty.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "tb_avaliacao")
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "estabelecimentoId")
    private Estabelecimento estabelecimento;
    @ManyToOne
    @JoinColumn(name = "profissionalId")
    private Profissional profissional;

    @ManyToOne
    @JoinColumn(name = "clienteId")
    private Cliente cliente;

    private Integer nota;
    private String comentario;

    public Avaliacao() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Avaliacao(Long id, String comentario, Integer nota, Cliente cliente, Profissional profissional, Estabelecimento estabelecimento) {
        this.id = id;
        this.comentario = comentario;
        this.nota = nota;
        this.cliente = cliente;
        this.profissional = profissional;
        this.estabelecimento = estabelecimento;
    }
}
