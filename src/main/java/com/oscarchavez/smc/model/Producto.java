package com.oscarchavez.smc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "productos")  // Se asegura de que la entidad se mapeará a la tabla 'productos'
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idproducto;

    private String nombre;
    private String proveedor;
    private Date fechaingreso;
    private String origen;
    private String anio;
    private String codigo;
    private String modelo;
    private String marca;
    private String pais;
    private String estado;
    private Double costo;
    private Double precioventa;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idfoto", referencedColumnName = "idfoto")
    private FotoProd fotoProd;

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    private List<Gasto> gastos;

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    private List<Nota> notas;

    
}