package com.oscarchavez.smc.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscarchavez.smc.dto.ProductoResDto;
import com.oscarchavez.smc.model.FotoProd;
import com.oscarchavez.smc.model.Producto;
import com.oscarchavez.smc.repository.FotoProdRepository;
import com.oscarchavez.smc.repository.ProductoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public ProductoResDto obtenerPorId(int idproducto) {
        Map<String, Object> result = productoRepository.productoxId(idproducto);

        LocalDate fechaIngresoLocalDate = ((java.sql.Date) result.get("fechaingreso")).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaIngresoFormateada = fechaIngresoLocalDate.format(formatter);

        String nombre = (String) result.get("nombre");
        String proveedor = (String) result.get("proveedor");
        String fechaingreso = fechaIngresoFormateada;

        String origen = (String) result.get("origen");
        String anio = ((String) result.get("anio"));
        String codigo = (String) result.get("codigo");
        String modelo = (String) result.get("modelo");

        String marca = (String) result.get("marca");
        String pais = (String) result.get("pais");
        String estado = (String) result.get("estado");
        double costo = ((double) result.get("costo"));
        double precioventa = ((double) result.get("precioventa"));
        // Formatea costo
        DecimalFormat df = new DecimalFormat("0.00");
        String costoSt = df.format(costo);

        // Determina estadoTexto
        String estadoTexto;
        switch (estado) {
            case "1":
                estadoTexto = "Mantenimiento";
                break;
            case "2":
                estadoTexto = "Exposicion";
                break;
            case "3":
                estadoTexto = "Vendido";
                break;
            case "4":
                estadoTexto = "Exportacion";
                break;
            case "0":
                estadoTexto = "Anulado";
                break;
            default:
                estadoTexto = "Desconocido";
                break;
        }

        return new ProductoResDto(
                idproducto, nombre, proveedor, fechaingreso, origen, anio, codigo, modelo, marca, pais,
                estado, estadoTexto, costo, precioventa, costoSt);
    }

    public Producto crear(Producto producto) {
        return productoRepository.save(producto);
    }

    public void eliminar(int id) {
        productoRepository.deleteById(id);
    }

    public List<Producto> listaProductos() {
        return productoRepository.listaProductos();
    }

    public List<ProductoResDto> listaProductoRes() {
        List<Map<String, Object>> results = productoRepository.findProductoData();
        List<ProductoResDto> productos = new ArrayList<>();

        for (Map<String, Object> result : results) {

            LocalDate fechaIngresoLocalDate = ((java.sql.Date) result.get("fechaingreso")).toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fechaIngresoFormateada = fechaIngresoLocalDate.format(formatter);

            int idproducto = ((Number) result.get("idproducto")).intValue();
            String nombre = (String) result.get("nombre");
            String proveedor = (String) result.get("proveedor");
            String fechaingreso = fechaIngresoFormateada;
            String origen = (String) result.get("origen");
            String anio = ((String) result.get("anio"));
            String codigo = (String) result.get("codigo");
            String modelo = (String) result.get("modelo");
            String marca = (String) result.get("marca");
            String pais = (String) result.get("pais");
            String estado = (String) result.get("estado");
            double costo = ((double) result.get("costo"));
            double precioventa = ((double) result.get("precioventa"));
            String urlimagen = (String) result.get("urlimagen");
            double gastototal = ((double) result.get("gastototal"));
            double manoobra = ((double) result.get("manoobra"));
            double materiales = ((double) result.get("materiales"));
            double repuestos = ((double) result.get("repuestos"));
            double otros = ((double) result.get("otros"));

            // Formatea costo
            DecimalFormat df = new DecimalFormat("0.00");
            String costoSt = df.format(costo);

            // Determina estadoTexto
            String estadoTexto;
            switch (estado) {
                case "1":
                    estadoTexto = "Mantenimiento";
                    break;
                case "2":
                    estadoTexto = "Exposicion";
                    break;
                case "3":
                    estadoTexto = "Vendido";
                    break;
                case "4":
                    estadoTexto = "Exportacion";
                    break;
                case "0":
                    estadoTexto = "Anulado";
                    break;
                default:
                    estadoTexto = "Desconocido";
                    break;
            }

            double gastoFinal = costo + gastototal;

            ProductoResDto dto = new ProductoResDto(
                    idproducto, nombre, proveedor, fechaingreso, urlimagen, origen, anio, codigo, modelo, marca, pais,
                    estado, costo, precioventa, gastototal, manoobra, materiales, repuestos, otros, estadoTexto,
                    costoSt, gastoFinal);
            productos.add(dto);
        }

        return productos;
    }

    public Producto getById(int idproducto) {
        // Utiliza el método findByIdproducto del repositorio
        return productoRepository.findByIdproducto(idproducto)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con id: " + idproducto));
    }

    public Producto getByIdproducto(int idproducto){
        return productoRepository.getByIdproducto(idproducto);
    }

    public Producto actualizarProducto(Integer id, Producto productoActualizado) {

        Optional<Producto> optionalProducto = productoRepository.findByIdproducto(id);
        if (optionalProducto.isPresent()) {
            Producto producto = optionalProducto.get();
            

            producto.setFotoProd(productoActualizado.getFotoProd());
            producto.setNombre(productoActualizado.getNombre());
            producto.setPais(productoActualizado.getPais());
            producto.setProveedor(productoActualizado.getProveedor());
            producto.setFechaingreso(productoActualizado.getFechaingreso());
            producto.setOrigen(productoActualizado.getOrigen());
            producto.setAnio(productoActualizado.getAnio());
            producto.setCodigo(productoActualizado.getCodigo());
            producto.setModelo(productoActualizado.getModelo());
            producto.setMarca(productoActualizado.getMarca());
            producto.setEstado(productoActualizado.getEstado());
            producto.setCosto(productoActualizado.getCosto());
            producto.setPrecioventa(productoActualizado.getPrecioventa());

            return productoRepository.save(producto);
        }

        return null;
    }

}
