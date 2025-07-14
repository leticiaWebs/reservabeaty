package com.reservabeaty.reservabeaty.delivery.controller;


import com.reservabeaty.reservabeaty.domain.models.Profissional;
import com.reservabeaty.reservabeaty.usecase.service.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {

    @Autowired
    private ProfissionalService service;
    @PostMapping
    public ResponseEntity<Profissional> criar(@RequestBody Profissional profissional) {
        return ResponseEntity.ok(service.criar(profissional));
    }

    @GetMapping
    public ResponseEntity<List<Profissional>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profissional> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/especialidade")
    public ResponseEntity<List<Profissional>> buscarPorEspecialidade(@RequestParam String especialidade) {
        return ResponseEntity.ok(service.buscarPorEspecialidade(especialidade));
    }

    @GetMapping("/tarifa")
    public ResponseEntity<List<Profissional>> buscarPorFaixaDeTarifa(@RequestParam Double min, @RequestParam Double max) {
        return ResponseEntity.ok(service.buscarPorFaixaDeTarifa(min, max));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profissional> atualizar(@PathVariable Long id, @RequestBody Profissional atualizado) {
        return ResponseEntity.ok(service.atualizar(id, atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
