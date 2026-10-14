package com.bodeganube.ordenes.controller;

import com.bodeganube.ordenes.model.Orden;
import com.bodeganube.ordenes.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    // aqui es donde evitamos pedidos duplicados
    @PostMapping("/webhook")
    public ResponseEntity<?> recibirWebhook(@RequestBody Orden nuevaOrden) {
        Optional<Orden> existente = ordenService.buscarPorCodigoWebhook(nuevaOrden.getCodigoWebhook());
        if (existente.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: Pedido duplicado. El webhook " + nuevaOrden.getCodigoWebhook() + " ya fue registrado.");
        }

        if (nuevaOrden.getEstado() == null || nuevaOrden.getEstado().isEmpty()) {
            nuevaOrden.setEstado("DISPONIBLE_PICKING");
        }

        return new ResponseEntity<>(ordenService.guardar(nuevaOrden), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Orden>> listarTodas() {
        return ResponseEntity.ok(ordenService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<Orden> orden = ordenService.obtenerPorId(id);
        if (orden.isPresent()) {
            return ResponseEntity.ok(orden.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Orden no encontrada");
    }

    // Operario: consultar ordenes disponibles para picking
    @GetMapping("/picking")
    public ResponseEntity<List<Orden>> listarParaPicking() {
        return ResponseEntity.ok(ordenService.listarPorEstado("DISPONIBLE_PICKING"));
    }

    // Comercio: consultar solo sus propias ordenes y su estado
    @GetMapping("/comercio/{nombreComercio}")
    public ResponseEntity<List<Orden>> listarPorComercio(@PathVariable String nombreComercio) {
        return ResponseEntity.ok(ordenService.listarPorComercio(nombreComercio));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        Optional<Orden> ordenExistente = ordenService.obtenerPorId(id);
        if (ordenExistente.isPresent()) {
            Orden o = ordenExistente.get();
            o.setEstado(estado);
            return ResponseEntity.ok(ordenService.guardar(o));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Orden no encontrada");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        if (ordenService.obtenerPorId(id).isPresent()) {
            ordenService.eliminar(id);
            return ResponseEntity.ok("Orden eliminada correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: La orden no existe");
    }
}