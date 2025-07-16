package com.reservabeaty.reservabeaty.application.usecase.service;

import com.reservabeaty.reservabeaty.domain.models.Estabelecimento;
import com.reservabeaty.reservabeaty.domain.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstabelecimentoService {

    @Autowired
    private EstabelecimentoRepository repository;

    public Estabelecimento criar(Estabelecimento estabelecimento) {
        return repository.save(estabelecimento);
    }

    public List<Estabelecimento> listarTodos() {
        return repository.findAll();
    }

    public Estabelecimento buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));
    }

    public Estabelecimento atualizar(Long id, Estabelecimento atualizado) {
        Estabelecimento estabelecimento = buscarPorId(id);
        estabelecimento.setNome(atualizado.getNome());
        estabelecimento.setEndereco(atualizado.getEndereco());
        estabelecimento.setServicos(atualizado.getServicos());
        estabelecimento.setProfissionais(atualizado.getProfissionais());
        estabelecimento.setHorariosFuncionamento(atualizado.getHorariosFuncionamento());
        estabelecimento.setFotos(atualizado.getFotos());
        return repository.save(estabelecimento);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public List<Estabelecimento> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Estabelecimento> buscarPorCidade(String cidade) {
        return repository.findByEnderecoCidadeContainingIgnoreCase(cidade);
    }

    public List<Estabelecimento> buscarPorBairro(String bairro) {
        return repository.findByEnderecoBairroContainingIgnoreCase(bairro);
    }
}
