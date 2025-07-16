package com.reservabeaty.reservabeaty.delivery.controller;


import com.reservabeaty.reservabeaty.domain.models.HorarioDisponivel;
import com.reservabeaty.reservabeaty.application.usecase.service.HorarioDisponivelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/horarios-disponiveis")
public class HorarioDisponivelController {
    @Autowired
    private HorarioDisponivelService service;

    @PostMapping
    public ResponseEntity<HorarioDisponivel> criar(@RequestBody HorarioDisponivel horario) {
        return ResponseEntity.ok(service.criar(horario));
    }

    @GetMapping
    public ResponseEntity<List<HorarioDisponivel>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorarioDisponivel> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/data")
    public ResponseEntity<List<HorarioDisponivel>> buscarPorData(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return ResponseEntity.ok(service.buscarPorData(data));
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<HorarioDisponivel>> buscarEntreDatas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.ok(service.buscarEntreDatas(inicio, fim));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorarioDisponivel> atualizar(@PathVariable Long id, @RequestBody HorarioDisponivel atualizado) {
        return ResponseEntity.ok(service.atualizar(id, atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
