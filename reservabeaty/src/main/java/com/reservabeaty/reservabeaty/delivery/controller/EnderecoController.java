package com.reservabeaty.reservabeaty.delivery.controller;


import com.reservabeaty.reservabeaty.domain.models.Endereco;
import com.reservabeaty.reservabeaty.usecase.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {
    @Autowired
    private EnderecoService service;

    @PostMapping
    public ResponseEntity<Endereco> criar(@RequestBody Endereco endereco) {
        return ResponseEntity.ok(service.criar(endereco));
    }

    @GetMapping
    public ResponseEntity<List<Endereco>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Endereco> atualizar(@PathVariable Long id, @RequestBody Endereco enderecoAtualizado) {
        return ResponseEntity.ok(service.atualizar(id, enderecoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cidade")
    public ResponseEntity<List<Endereco>> buscarPorCidade(@RequestParam String cidade) {
        return ResponseEntity.ok(service.buscarPorCidade(cidade));
    }

    @GetMapping("/bairro")
    public ResponseEntity<List<Endereco>> buscarPorBairro(@RequestParam String bairro) {
        return ResponseEntity.ok(service.buscarPorBairro(bairro));
    }

    @GetMapping("/cep/{cep}")
    public ResponseEntity<Endereco> buscarPorCep(@PathVariable String cep) {
        return ResponseEntity.ok(service.buscarPorCep(cep));
    }
}
