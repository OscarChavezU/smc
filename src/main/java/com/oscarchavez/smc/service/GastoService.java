package com.oscarchavez.smc.service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscarchavez.smc.dto.GastoDto;
import com.oscarchavez.smc.model.Gasto;
import com.oscarchavez.smc.model.Producto;
import com.oscarchavez.smc.repository.GastoRepository;
import com.oscarchavez.smc.repository.ProductoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<GastoDto> ListaGastos(int idproducto) {

        List<GastoDto> results = gastoRepository.ListaGastos(idproducto);

        for (GastoDto result : results) {

            double monto = result.getMonto();
            DecimalFormat df = new DecimalFormat("0.00");
            String montoSt = df.format(monto);

            result.setMontoSt(montoSt);

        }
        return results;

    }

    public List<GastoDto> ListaGastosxTipo(int idproducto, String tipogasto) {

        List<GastoDto> results = gastoRepository.ListaGastosxTipo(idproducto, tipogasto);
        for (GastoDto result : results) {

            double monto = result.getMonto();
            DecimalFormat df = new DecimalFormat("0.00");
            String montoSt = df.format(monto);

            result.setMontoSt(montoSt);

        }

        return results;
    }

    public Gasto insertarGasto(Gasto gasto) {
        if (gasto.getProducto() != null && gasto.getProducto().getIdproducto() != null) {
            // Busca el producto en la base de datos para asegurarse de que existe
            Producto producto = productoRepository.findById(gasto.getProducto().getIdproducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            gasto.setProducto(producto);
        }
        return gastoRepository.save(gasto);
    }

    public Gasto actualizarGasto(Gasto gastoActualizado) {
        // Verifica si el gasto existe
        Optional<Gasto> optionalGasto = gastoRepository.findById(gastoActualizado.getIdgasto());
        if (optionalGasto.isPresent()) {
            Gasto gasto = optionalGasto.get();

            // Actualiza los campos del gasto sin modificar el idproducto
            gasto.setNombre(gastoActualizado.getNombre());
            gasto.setFecharegistro(gastoActualizado.getFecharegistro());
            gasto.setTipogasto(gastoActualizado.getTipogasto());
            gasto.setDescripcion(gastoActualizado.getDescripcion());
            gasto.setMonto(gastoActualizado.getMonto());
            gasto.setEstado(gastoActualizado.getEstado());
            gasto.setObservaciones(gastoActualizado.getObservaciones());

            // No actualices el idproducto si está presente en el objeto recibido
            // Si el objeto recibido tiene un idproducto diferente al actual, lo ignoramos
            if (gastoActualizado.getProducto() != null) {
                // Opcional: puedes actualizar la relación si es necesario
                // gasto.setProducto(gastoActualizado.getProducto());
            }

            // Guarda el gasto actualizado en la base de datos
            return gastoRepository.save(gasto);
        }

        return null;
    }

    public Gasto getById(int idgasto) {
        // Utiliza el método findByIdproducto del repositorio
        return gastoRepository.findById(idgasto)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con id: " + idgasto));
    }


    // Método para actualizar el estado de un Gasto
    public Gasto anularGasto(Integer idgasto) {
        // Buscar el Gasto por idgasto
        Gasto gasto = gastoRepository.findById(idgasto).orElse(null);
        if (gasto != null) {
            // Actualizar el estado a "0"
            gasto.setEstado("0");
            // Guardar los cambios en la base de datos
            return gastoRepository.save(gasto);
        }
        return null;
    }

}
