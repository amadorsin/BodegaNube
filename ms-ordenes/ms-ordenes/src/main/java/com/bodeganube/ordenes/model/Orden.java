package com.bodeganube.ordenes.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "ordenes")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String codigoWebhook;
    private String comercio;
    private String estado; // como podria ser: "DISPONIBLE_PICKING", "SIN_STOCK", "DESPACHADO"

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    private List<DetalleOrden> detalles;

    public Orden() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigoWebhook() { return codigoWebhook; }
    public void setCodigoWebhook(String codigoWebhook) { this.codigoWebhook = codigoWebhook; }

    public String getComercio() { return comercio; }
    public void setComercio(String comercio) { this.comercio = comercio; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<DetalleOrden> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleOrden> detalles) { this.detalles = detalles; }
}