package com.bodeganube.inventario.controller;

import com.bodeganube.inventario.model.Categoria;
import com.bodeganube.inventario.model.Producto;
import com.bodeganube.inventario.repository.CategoriaRepository;
import com.bodeganube.inventario.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @PostMapping("/categorias")
    public ResponseEntity<Categoria> crearCategoria(@RequestBody Categoria categoria) {
        return new ResponseEntity<>(categoriaRepository.save(categoria), HttpStatus.CREATED);
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> listarCategorias() {
        return ResponseEntity.ok(categoriaRepository.findAll());
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<Producto> prod = productoService.obtenerPorId(id);
        if (prod.isPresent()) {
            return ResponseEntity.ok(prod.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Producto no encontrado");
    }

    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        return new ResponseEntity<>(productoService.guardar(producto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Producto datos) {
        Optional<Producto> prodExistente = productoService.obtenerPorId(id);
        if (prodExistente.isPresent()) {
            Producto p = prodExistente.get();
            p.setNombre(datos.getNombre());
            p.setSku(datos.getSku());
            p.setStock(datos.getStock());
            p.setCategoria(datos.getCategoria());
            return ResponseEntity.ok(productoService.guardar(p));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No se puede actualizar, ID no existe");
    }

    @PutMapping("/{id}/descontar")
    public ResponseEntity<?> descontarStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        Optional<Producto> prodExistente = productoService.obtenerPorId(id);
        if (prodExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Producto no encontrado");
        }
        Producto p = prodExistente.get();
        if (p.getStock() < cantidad) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Stock insuficiente");
        }
        p.setStock(p.getStock() - cantidad);
        return ResponseEntity.ok(productoService.guardar(p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        if (productoService.obtenerPorId(id).isPresent()) {
            productoService.eliminar(id);
            return ResponseEntity.ok("Producto eliminado correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Producto no existe");
    }
}