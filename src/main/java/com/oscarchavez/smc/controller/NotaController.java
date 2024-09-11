package com.oscarchavez.smc.controller;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oscarchavez.smc.dto.NotaDto;
import com.oscarchavez.smc.model.Gasto;
import com.oscarchavez.smc.model.Nota;
import com.oscarchavez.smc.model.Producto;
import com.oscarchavez.smc.service.NotaService;
import com.oscarchavez.smc.service.ProductoService;

@RestController
@RequestMapping("/api/notas")
public class NotaController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private NotaService notaService;

    // TODO: Implementar CRUD para Notas

    @PostMapping("/insNota")
    public ResponseEntity<Nota> insNota(@RequestBody NotaDto notadto) {
        // Implementación de creación de Nota

        Nota nota = new Nota();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Producto producto = productoService.getById(notadto.getIdproducto());
        if (producto == null) {
            return ResponseEntity.badRequest().body(null);
        }

        nota.setDescripcion(notadto.getDescripcion());
        nota.setIdnota(notadto.getIdnota());
        nota.setFecha(notadto.getFecha());
        nota.setProducto(producto);
        nota.setEstado("2");

        nota = notaService.inserNota(nota);

        return ResponseEntity.ok(nota);
    }

    @PostMapping("/updNota")
    public ResponseEntity<Nota> updNota(@RequestParam("idnota") int idnota,
            @RequestParam("estado") String estado) {
        // Implementación de creación de Nota

        return ResponseEntity.ok(notaService.updateNota(idnota, estado));
    }

}
