package com.oscarchavez.smc.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "gastos")
@Data
@NoArgsConstructor
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idgasto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idproducto", referencedColumnName = "idproducto")
    private Producto producto;

    private String nombre;
    private String fecharegistro;
    private String tipogasto;
    private String descripcion;
    private Double monto;
    private String estado;
    private String observaciones;
}