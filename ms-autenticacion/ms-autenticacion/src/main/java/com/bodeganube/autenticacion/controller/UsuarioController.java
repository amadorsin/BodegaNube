package com.bodeganube.autenticacion.controller;

import com.bodeganube.autenticacion.model.Usuario;
import com.bodeganube.autenticacion.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.obtenerPorId(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Usuario no encontrado");
    }

    @PostMapping
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        return new ResponseEntity<>(usuarioService.guardar(usuario), HttpStatus.CREATED);
    }

    // Endpoint de Login para autenticar según el rol del usuario
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario credenciales) {
        Optional<Usuario> usuario = usuarioService.buscarPorUsername(credenciales.getUsername());
        if (usuario.isPresent() && usuario.get().getPassword().equals(credenciales.getPassword())) {
            String tokenSimulado = "Bearer eyJhbGciOiJIUzI1NiJ9.Usuario-" + usuario.get().getUsername() + "-Rol-" + usuario.get().getRol();
            return ResponseEntity.ok(tokenSimulado);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error 401: Credenciales inválidas");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Usuario datos) {
        Optional<Usuario> existente = usuarioService.obtenerPorId(id);
        if (existente.isPresent()) {
            Usuario u = existente.get();
            u.setUsername(datos.getUsername());
            u.setPassword(datos.getPassword());
            u.setRol(datos.getRol());
            return ResponseEntity.ok(usuarioService.guardar(u));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Usuario no encontrado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        if (usuarioService.obtenerPorId(id).isPresent()) {
            usuarioService.eliminar(id);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Usuario no existe");
    }
}