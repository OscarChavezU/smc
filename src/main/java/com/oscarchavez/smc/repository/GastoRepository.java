package com.oscarchavez.smc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oscarchavez.smc.dto.GastoDto;
import com.oscarchavez.smc.model.Gasto;

public interface GastoRepository extends JpaRepository<Gasto, Integer> {

    @Query("SELECT new com.oscarchavez.smc.dto.GastoDto( " +
            "g.idgasto, g.producto.idproducto, g.nombre, g.fecharegistro, g.tipogasto, g.descripcion, g.monto, g.estado, g.observaciones) "+
            "FROM Gasto g WHERE g.producto.idproducto = :idproducto and g.estado <> '0'")
    List<GastoDto> ListaGastos(@Param("idproducto") int idproducto);

    @Query("SELECT new com.oscarchavez.smc.dto.GastoDto( " +
            "g.idgasto, g.producto.idproducto, g.nombre, g.fecharegistro, g.tipogasto, g.descripcion, g.monto, g.estado, g.observaciones) "+
            "FROM Gasto g WHERE g.producto.idproducto = :idproducto and g.estado <> '0' and g.tipogasto = :tipogasto")
    List<GastoDto> ListaGastosxTipo(@Param("idproducto") int idproducto, @Param("tipogasto") String tipogasto);

}
