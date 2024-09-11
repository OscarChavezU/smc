package com.oscarchavez.smc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GastoDto {

    private Integer idgasto;
    private Integer idproducto;
    private String nombre;
    private String fecharegistro;
    private String tipogasto;
    private String descripcion;
    private Double monto;
    private String estado;
    private String observaciones;
    private String montoSt;
    
    public GastoDto(Integer idgasto, Integer idproducto, String nombre, String fecharegistro, String tipogasto,
            String descripcion, Double monto, String estado, String observaciones) {
        this.idgasto = idgasto;
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.fecharegistro = fecharegistro;
        this.tipogasto = tipogasto;
        this.descripcion = descripcion;
        this.monto = monto;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    
}
