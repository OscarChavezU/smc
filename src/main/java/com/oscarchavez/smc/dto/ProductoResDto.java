package com.oscarchavez.smc.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoResDto {
    private Integer idproducto;
    private String nombre;
    private String proveedor;
    private String fechaingreso;
    private String urlimagen;
    private String origen;
    private String anio;
    private String codigo;
    private String modelo;
    private String marca;
    private String pais;
    private String estado;
    private String estadoTexto;
    private String costoSt;
    private Double costo;
    private Double precioventa;
    private Double gastototal;
    private Double manoobra;
    private Double materiales;
    private Double repuestos;
    private Double otros;
    private Double gastoFinal;

    public ProductoResDto(
            int idproducto, String nombre, String proveedor, String fechaingreso, String urlimagen, String origen,
            String anio, String codigo, String modelo, String marca, String pais,
            String estado, double costo, double precioventa, double gastototal, double manoobra, double materiales,
            double repuestos, double otros, String estadoTexto, String costoSt, double gastoFinal) {

        this.idproducto = idproducto;
        this.nombre = nombre;
        this.proveedor = proveedor;
        this.fechaingreso = fechaingreso;
        this.urlimagen = urlimagen;
        this.origen = origen;
        this.anio = anio;
        this.codigo = codigo;
        this.modelo = modelo;
        this.marca = marca;
        this.pais = pais;
        this.estado = estado;
        this.costo = costo;
        this.precioventa = precioventa;
        this.gastototal = gastototal;
        this.manoobra = manoobra;
        this.materiales = materiales;
        this.repuestos = repuestos;
        this.otros = otros;
        this.estadoTexto = estadoTexto;
        this.costoSt = costoSt;
        this.gastoFinal = gastoFinal;

    }

    public ProductoResDto(
            int idproducto, String nombre, String proveedor, String fechaingreso, String origen, String anio,
            String codigo, String modelo, String marca, String pais,
            String estado, String estadoTexto, double costo, double precioventa, String costoSt) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.proveedor = proveedor;
        this.fechaingreso = fechaingreso;
        this.origen = origen;
        this.anio = anio;
        this.codigo = codigo;
        this.modelo = modelo;
        this.marca = marca;
        this.pais = pais;
        this.estado = estado;
        this.costo = costo;
        this.precioventa = precioventa;
        
        this.estadoTexto = estadoTexto;
        this.costoSt = costoSt;
        
    }

}
