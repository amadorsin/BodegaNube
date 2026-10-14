package com.bodeganube.ordenes.repository;

import com.bodeganube.ordenes.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {
    Optional<Orden> findByCodigoWebhook(String codigoWebhook);
    List<Orden> findByComercio(String comercio);
    List<Orden> findByEstado(String estado);
}