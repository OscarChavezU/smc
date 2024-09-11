package com.oscarchavez.smc.controller;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oscarchavez.smc.dto.GastoDto;
import com.oscarchavez.smc.dto.NotaDto;
import com.oscarchavez.smc.dto.ProductoResDto;
import com.oscarchavez.smc.model.Gasto;
import com.oscarchavez.smc.model.Marca;
import com.oscarchavez.smc.model.Nota;
import com.oscarchavez.smc.model.Producto;
import com.oscarchavez.smc.service.GastoService;
import com.oscarchavez.smc.service.MarcaService;
import com.oscarchavez.smc.service.NotaService;
import com.oscarchavez.smc.service.ProductoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/gastos")
public class GastoController {

    @Autowired
    private GastoService gastoService;
    @Autowired
    private MarcaService marcaService;
    @Autowired
    private NotaService notaService;
    @Autowired
    private ProductoService productoService;

    @GetMapping("/getCostos/{idproducto}")
    public ResponseEntity<Map<String, Object>> getCostos(@PathVariable int idproducto) {

        List<GastoDto> listaGastos = gastoService.ListaGastos(idproducto);
        List<GastoDto> listaManoObra = gastoService.ListaGastosxTipo(idproducto, "Mano de Obra");
        List<GastoDto> listaRepuestos = gastoService.ListaGastosxTipo(idproducto, "Repuestos");
        List<GastoDto> listaMateriales = gastoService.ListaGastosxTipo(idproducto, "Materiales");
        List<GastoDto> listaOtros = gastoService.ListaGastosxTipo(idproducto, "Otros");

        List<NotaDto> listaNotas = notaService.listaNotas(idproducto);
        ProductoResDto producto = productoService.obtenerPorId(idproducto);

        double totalManoObra = 0;
        double totalRepuestos = 0;
        double totalMateriales = 0;
        double totalOtros = 0;

        for (GastoDto gasto : listaManoObra) {
            totalManoObra += gasto.getMonto();
        }
        for (GastoDto gasto : listaRepuestos) {
            totalRepuestos += gasto.getMonto();
        }
        for (GastoDto gasto : listaMateriales) {
            totalMateriales += gasto.getMonto();
        }
        for (GastoDto gasto : listaOtros) {
            totalOtros += gasto.getMonto();
        }

        List<Marca> listaMarcas = marcaService.listarMarcas("1");

        DecimalFormat df = new DecimalFormat("0.00");

        // Construimos la respuesta en un mapa
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("listaMarcas", listaMarcas);
        responseMap.put("listaNotas", listaNotas);
        responseMap.put("listaGastos", listaGastos);
        responseMap.put("listaManoObra", listaManoObra);
        responseMap.put("listaRepuestos", listaRepuestos);
        responseMap.put("listaMateriales", listaMateriales);
        responseMap.put("listaOtros", listaOtros);
        responseMap.put("producto", producto);
        responseMap.put("totalManoObra", df.format(totalManoObra));
        responseMap.put("totalRepuestos", df.format(totalRepuestos));
        responseMap.put("totalMateriales", df.format(totalMateriales));
        responseMap.put("totalOtros", df.format(totalOtros));

        // Retornamos la respuesta con el cuerpo y el código de estado OK (200)
        return ResponseEntity.ok(responseMap);
    }

    @PostMapping("/updGasto")
    public ResponseEntity<Gasto> updateGasto(@RequestParam("idgasto") Integer idgasto,
            @RequestParam("fecharegistro") String fecharegistro,
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("monto") Double monto,
            @RequestParam("observaciones") String observaciones) {
        try {

            // Buscar el Gasto existente por idgasto
            Gasto gastoExistente = gastoService.getById(idgasto);
            if (gastoExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Actualizar los campos del Gasto, excluyendo tipogasto
            gastoExistente.setFecharegistro(fecharegistro);
            gastoExistente.setNombre(nombre);
            gastoExistente.setDescripcion(descripcion);
            gastoExistente.setMonto(monto);
            gastoExistente.setObservaciones(observaciones);
            gastoExistente.setEstado("1");

            // Buscar el Producto por idproducto y asignarlo al Gasto
            Producto producto = productoService.getById(gastoExistente.getProducto().getIdproducto());
            if (producto != null) {
                gastoExistente.setProducto(producto);
            }

            // Guardar el Gasto actualizado
            Gasto gastoActualizado = gastoService.actualizarGasto(gastoExistente);
            return ResponseEntity.status(HttpStatus.OK).body(gastoActualizado);
        } catch (Exception e) {
            e.printStackTrace(); // Loguea el error para depuración
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/insGasto")
    public ResponseEntity<Gasto> insertarGasto(
            @RequestParam("fecharegistro") String fecharegistro,
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("monto") Double monto,
            @RequestParam(value = "tipogasto") String tipogasto,
            @RequestParam("observaciones") String observaciones,
            @RequestParam("idproducto") Integer idproducto) {
        try {
            if (idproducto == null) {
                return ResponseEntity.badRequest().body(null);
            }

            // Crear un nuevo objeto Gasto
            Gasto gasto = new Gasto();
            gasto.setFecharegistro(fecharegistro);
            gasto.setNombre(nombre);
            gasto.setDescripcion(descripcion);
            gasto.setMonto(monto);
            gasto.setTipogasto(tipogasto);
            gasto.setObservaciones(observaciones);
            gasto.setEstado("1");

            // Buscar el Producto por idproducto
            Producto producto = productoService.getById(idproducto);
            if (producto == null) {
                return ResponseEntity.badRequest().body(null);
            }
            gasto.setProducto(producto);

            // Guardar el nuevo Gasto
            Gasto nuevoGasto = gastoService.insertarGasto(gasto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoGasto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/anularGasto")
    public ResponseEntity<Gasto> actualizarEstado(
            @RequestParam("idgasto") Integer idgasto
    ) {
        try {
            // Llamar al servicio para actualizar el estado del Gasto
            Gasto gastoActualizado = gastoService.anularGasto(idgasto);
            if (gastoActualizado != null) {
                return ResponseEntity.status(HttpStatus.OK).body(gastoActualizado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
