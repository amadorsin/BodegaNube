package com.bodeganube.ordenes.service;

import com.bodeganube.ordenes.model.DetalleOrden;
import com.bodeganube.ordenes.model.Orden;
import com.bodeganube.ordenes.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    public List<Orden> listarTodas() {
        return ordenRepository.findAll();
    }

    public Optional<Orden> obtenerPorId(Long id) {
        return ordenRepository.findById(id);
    }

    public Optional<Orden> buscarPorCodigoWebhook(String codigo) {
        return ordenRepository.findByCodigoWebhook(codigo);
    }

    public List<Orden> listarPorEstado(String estado) {
        return ordenRepository.findByEstado(estado);
    }

    public List<Orden> listarPorComercio(String comercio) {
        return ordenRepository.findByComercio(comercio);
    }

    public Orden guardar(Orden orden) {
        if (orden.getDetalles() != null) {
            for (DetalleOrden detalle : orden.getDetalles()) {
                detalle.setOrden(orden);
            }
        }
        return ordenRepository.save(orden);
    }

    public void eliminar(Long id) {
        ordenRepository.deleteById(id);
    }
}