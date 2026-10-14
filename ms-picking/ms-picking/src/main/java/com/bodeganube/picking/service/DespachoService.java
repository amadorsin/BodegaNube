package com.bodeganube.picking.service;

import com.bodeganube.picking.model.Despacho;
import com.bodeganube.picking.repository.DespachoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DespachoService {

    @Autowired
    private DespachoRepository despachoRepository;

    public List<Despacho> listarTodos() {
        return despachoRepository.findAll();
    }

    public Optional<Despacho> obtenerPorId(Long id) {
        return despachoRepository.findById(id);
    }

    public Despacho guardar(Despacho despacho) {
        return despachoRepository.save(despacho);
    }

    public void eliminar(Long id) {
        despachoRepository.deleteById(id);
    }
}