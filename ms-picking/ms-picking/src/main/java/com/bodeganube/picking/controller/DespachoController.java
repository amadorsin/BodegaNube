package com.bodeganube.picking.controller;

import com.bodeganube.picking.model.Despacho;
import com.bodeganube.picking.service.DespachoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/despachos")
public class DespachoController {

    @Autowired
    private DespachoService despachoService;

    @GetMapping
    public ResponseEntity<List<Despacho>> listar() {
        return ResponseEntity.ok(despachoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<Despacho> despacho = despachoService.obtenerPorId(id);
        if (despacho.isPresent()) {
            return ResponseEntity.ok(despacho.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Despacho no encontrado");
    }

    // registra el picking/despacho y su numero de seguimiento
    @PostMapping
    public ResponseEntity<Despacho> crear(@RequestBody Despacho despacho) {
        if (despacho.getEstado() == null || despacho.getEstado().isEmpty()) {
            despacho.setEstado("DESPACHADO");
        }
        return new ResponseEntity<>(despachoService.guardar(despacho), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Despacho datos) {
        Optional<Despacho> existente = despachoService.obtenerPorId(id);
        if (existente.isPresent()) {
            Despacho d = existente.get();
            d.setOrdenId(datos.getOrdenId());
            d.setOperario(datos.getOperario());
            d.setNumeroSeguimiento(datos.getNumeroSeguimiento());
            d.setEstado(datos.getEstado());
            return ResponseEntity.ok(despachoService.guardar(d));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Despacho no existe");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        if (despachoService.obtenerPorId(id).isPresent()) {
            despachoService.eliminar(id);
            return ResponseEntity.ok("Despacho eliminado correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Despacho no existe");
    }
}