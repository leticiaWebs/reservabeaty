package com.reservabeaty.reservabeaty.application.usecase.service;

import com.reservabeaty.reservabeaty.domain.models.Endereco;
import com.reservabeaty.reservabeaty.domain.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;

    public Endereco criar(Endereco endereco) {
        // Opcional: verificar se o CEP já existe no banco
        if (repository.findByCep(endereco.getCep()).isPresent()) {
            throw new RuntimeException("Endereço com este CEP já cadastrado.");
        }
        return repository.save(endereco);
    }

    public List<Endereco> listarTodos() {
        return repository.findAll();
    }

    public Endereco buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
    }

    public Endereco atualizar(Long id, Endereco enderecoAtualizado) {
        Endereco endereco = buscarPorId(id);
        endereco.setRua(enderecoAtualizado.getRua());
        endereco.setNumero(enderecoAtualizado.getNumero());
        endereco.setBairro(enderecoAtualizado.getBairro());
        endereco.setCidade(enderecoAtualizado.getCidade());
        endereco.setEstado(enderecoAtualizado.getEstado());
        endereco.setCep(enderecoAtualizado.getCep());
        return repository.save(endereco);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public List<Endereco> buscarPorCidade(String cidade) {
        return repository.findByCidadeContainingIgnoreCase(cidade);
    }

    public List<Endereco> buscarPorBairro(String bairro) {
        return repository.findByBairroContainingIgnoreCase(bairro);
    }

    public Endereco buscarPorCep(String cep) {
        return repository.findByCep(cep)
                .orElseThrow(() -> new RuntimeException("CEP não encontrado"));
    }
}
