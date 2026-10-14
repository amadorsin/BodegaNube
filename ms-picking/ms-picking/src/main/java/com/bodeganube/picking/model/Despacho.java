package com.bodeganube.picking.model;

import jakarta.persistence.*;

@Entity
@Table(name = "despachos")
public class Despacho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ordenId;
    private String operario;
    private String numeroSeguimiento;
    private String estado; // seria: "EN_PICKING", "DESPACHADO"

    public Despacho() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOrdenId() { return ordenId; }
    public void setOrdenId(Long ordenId) { this.ordenId = ordenId; }

    public String getOperario() { return operario; }
    public void setOperario(String operario) { this.operario = operario; }

    public String getNumeroSeguimiento() { return numeroSeguimiento; }
    public void setNumeroSeguimiento(String numeroSeguimiento) { this.numeroSeguimiento = numeroSeguimiento; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}