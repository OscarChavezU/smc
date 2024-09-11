package com.oscarchavez.smc.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.oscarchavez.smc.dto.ProductoResDto;
import com.oscarchavez.smc.model.Producto;
import com.oscarchavez.smc.model.FotoProd;
import com.oscarchavez.smc.model.Gasto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Query("SELECT p FROM Producto p "+
    "WHERE p.estado <> '0'")
    List<Producto> listaProductos();

    
    @Query(value = "SELECT " +
                   "    p.idproducto AS idproducto, " +
                   "    p.nombre, " +
                   "    p.proveedor, " +
                   "    p.fechaingreso, " +
                   "    p.idfoto, " +
                   "    p.origen, " +
                   "    p.anio, " +
                   "    p.codigo, " +
                   "    p.modelo, " +
                   "    p.marca, " +
                   "    p.pais, " +
                   "    p.estado, " +
                   "    p.costo, " +
                   "    p.precioventa, " +
                   "    f.urlimagen, " +
                   "    COALESCE(SUM(g.monto), 0) AS gastototal, " +
                   "    COALESCE(SUM(CASE WHEN g.tipogasto = 'Mano de Obra' THEN g.monto ELSE 0 END), 0) AS manoobra, " +
                   "    COALESCE(SUM(CASE WHEN g.tipogasto = 'Materiales' THEN g.monto ELSE 0 END), 0) AS materiales, " +
                   "    COALESCE(SUM(CASE WHEN g.tipogasto = 'Repuestos' THEN g.monto ELSE 0 END), 0) AS repuestos, " +
                   "    COALESCE(SUM(CASE WHEN g.tipogasto = 'Otros' THEN g.monto ELSE 0 END), 0) AS otros " +
                   "FROM " +
                   "    productos p " +
                   "INNER JOIN " +
                   "    fotosprod f ON p.idfoto = f.idfoto " +
                   "LEFT JOIN " +
                   "    gastos g ON p.idproducto = g.idproducto AND g.estado = '1' " +
                   "WHERE " +
                   "    p.estado NOT LIKE '0' " +
                   "GROUP BY " +
                   "    p.idproducto, " +
                   "    p.nombre, " +
                   "    p.proveedor, " +
                   "    p.fechaingreso, " +
                   "    p.idfoto, " +
                   "    p.origen, " +
                   "    p.anio, " +
                   "    p.codigo, " +
                   "    p.modelo, " +
                   "    p.marca, " +
                   "    p.pais, " +
                   "    p.estado, " +
                   "    p.costo, " +
                   "    p.precioventa, " +
                   "    f.urlimagen " +
                   "ORDER BY p.idproducto DESC", nativeQuery = true)
    List<Map<String, Object>> findProductoData();


    @Query(value = "SELECT idproducto, nombre, proveedor, fechaingreso, idfoto, origen, anio, codigo, modelo, marca, pais, estado, costo, precioventa FROM productos WHERE estado <> '0' AND idproducto = :idproducto", nativeQuery = true)
    Map<String, Object> productoxId(int idproducto);

    Optional<Producto> findByIdproducto(int idproducto);
    Producto getByIdproducto(int idproducto);
}
