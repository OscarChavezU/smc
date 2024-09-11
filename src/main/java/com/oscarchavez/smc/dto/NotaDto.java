package com.oscarchavez.smc.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotaDto {
    private int idnota;
    private String descripcion;
    private String estado;
    private Date fecha;
    private int idproducto;
    private String estadoTexto;

    // get y setters

    public void setEstado(String estado) {
        if (estado.equals("1")) {
            this.setEstadoTexto("Atendido");
        } else if (estado.equals("2")) {
            this.setEstadoTexto("Pendiente");
        } else if (estado.equals("0")) {
            this.setEstadoTexto("Anulado");
        }
    }

    public NotaDto(int idnota, String descripcion, String estado, Date fecha, int idproducto) {
        this.idnota = idnota;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fecha = fecha;
        this.idproducto = idproducto;

        if (estado.equals("1")) {
            this.estadoTexto = "Atendido";
        } else if (estado.equals("2")) {
            this.estadoTexto = "Pendiente";
        } else if (estado.equals("0")) {
            this.estadoTexto = "Anulado";
        }
    }

}
