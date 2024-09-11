package com.oscarchavez.smc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oscarchavez.smc.cloudinary.CloudinaryService;
import com.oscarchavez.smc.dto.ProductoResDto;
import com.oscarchavez.smc.model.FotoProd;
import com.oscarchavez.smc.model.Marca;
import com.oscarchavez.smc.model.Producto;
import com.oscarchavez.smc.service.FotoProdService;
import com.oscarchavez.smc.service.MarcaService;
import com.oscarchavez.smc.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private MarcaService marcaService;
    @Autowired
    private FotoProdService fotoProdService;
    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping
    public List<Producto> obtenerTodos() {
        return productoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResDto> obtenerPorId(@PathVariable int id) {
        ProductoResDto producto = productoService.obtenerPorId(id);
        return producto != null ? ResponseEntity.ok(producto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        return productoService.crear(producto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listaProductosRes")
    public ResponseEntity<Map<String, Object>> listaProductosRes() {
        List<ProductoResDto> listaProductos = productoService.listaProductoRes();
        List<Marca> listaMarcas2 = marcaService.listarMarcas("1");

        Map<String, Object> response = new HashMap<>();

        // Verificar si ambas listas son nulas o están vacías
        if ((listaProductos == null || listaProductos.isEmpty()) &&
                (listaMarcas2 == null || listaMarcas2.isEmpty())) {
            response.put("msg", "sin acceso a DB");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }

        // Agregar las listas al response solo si no están vacías
        if (listaProductos != null && !listaProductos.isEmpty()) {
            response.put("listaProductos", listaProductos);
        }

        if (listaMarcas2 != null && !listaMarcas2.isEmpty()) {
            response.put("listaMarcas", listaMarcas2);
        }

        // Devolver el mapa con las listas y el código de estado 200 (OK)
        return ResponseEntity.ok(response);
    }

    @GetMapping("/custom")
    public ResponseEntity<Map<String, Object>> custom() {
        List<ProductoResDto> listaProductos = productoService.listaProductoRes();
        Map<String, Object> response = new HashMap<>();
        response.put("listaProductos", listaProductos);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/ins")
    public ResponseEntity<Producto> registrar(
            @RequestParam("nombre") String nombre,
            @RequestParam("pais") String pais,
            @RequestParam("proveedor") String proveedor,
            @RequestParam("fechaingreso") String fechaingreso,
            @RequestParam("origen") String origen,
            @RequestParam("anio") String anio,
            @RequestParam("codigo") String codigo,
            @RequestParam("modelo") String modelo,
            @RequestParam("marca") String marca,
            @RequestParam("estado") String estado,
            @RequestParam("costo") double costo,
            @RequestParam("precioventa") double precioventa,
            @RequestParam("quiereFoto") String quiereFoto,
            @RequestPart(value = "foto", required = false) MultipartFile foto

    ) {
        try {

            // se registra una foto vacía
            FotoProd fotoProd = fotoProdService.createFotoProd();

            if (foto != null && !foto.isEmpty()) {
                String url = cloudinaryService.uploadFile(foto,fotoProd.getIdfoto());
                fotoProd = fotoProdService.updateFotoProdUrl(fotoProd.getIdfoto(), url);
            }

            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

            // se configura el producto con su foto
            Producto nuevoProducto = new Producto();
            nuevoProducto.setFotoProd(fotoProd);
            nuevoProducto.setNombre(nombre);
            nuevoProducto.setPais(pais);
            nuevoProducto.setProveedor(proveedor);
            nuevoProducto.setFechaingreso(formatoFecha.parse(fechaingreso));
            nuevoProducto.setOrigen(origen);
            nuevoProducto.setAnio(anio);
            nuevoProducto.setCodigo(codigo);
            nuevoProducto.setModelo(modelo);
            nuevoProducto.setMarca(marca);
            nuevoProducto.setEstado(estado);
            nuevoProducto.setCosto(costo);
            nuevoProducto.setPrecioventa(precioventa);

            Producto productoRegistrado = productoService.crear(nuevoProducto);
            return ResponseEntity.status(HttpStatus.CREATED).body(productoRegistrado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/actualizar")
    public ResponseEntity<Producto> actualizar(
            @RequestParam("idproducto") int idproducto,
            @RequestParam("nombre") String nombre,
            @RequestParam("pais") String pais,
            @RequestParam("proveedor") String proveedor,
            @RequestParam("fechaingreso") String fechaingreso,
            @RequestParam("origen") String origen,
            @RequestParam("anio") String anio,
            @RequestParam("codigo") String codigo,
            @RequestParam("modelo") String modelo,
            @RequestParam("marca") String marca,
            @RequestParam("estado") String estado,
            @RequestParam("costo") double costo,
            @RequestParam("precioventa") double precioventa,
            @RequestParam("quiereFoto") String quiereFoto,
            @RequestPart(value = "foto", required = false) MultipartFile foto) {

        try {

            // Obtener el producto actual desde la base de datos usando el idproducto
            Producto productoActual = productoService.getById(idproducto);
            if (productoActual == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // se registra una foto vacía
            FotoProd fotoProd = productoActual.getFotoProd();

            if (foto != null && !foto.isEmpty() && quiereFoto.equals("SI")) {
                String url = cloudinaryService.uploadFile(foto,fotoProd.getIdfoto());
                fotoProd = fotoProdService.updateFotoProdUrl(fotoProd.getIdfoto(), url);
            }

            // Actualizar los campos del producto
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            productoActual.setNombre(nombre);
            productoActual.setPais(pais);
            productoActual.setProveedor(proveedor);
            productoActual.setFechaingreso(formatoFecha.parse(fechaingreso));
            productoActual.setOrigen(origen);
            productoActual.setAnio(anio);
            productoActual.setCodigo(codigo);
            productoActual.setModelo(modelo);
            productoActual.setMarca(marca);
            productoActual.setEstado(estado);
            productoActual.setCosto(costo);
            productoActual.setPrecioventa(precioventa);
            productoActual.setFotoProd(fotoProd); // Asignar la foto actualizada (si corresponde)

            // Guardar el producto actualizado en la base de datos
            Producto productoActualizado = productoService.actualizarProducto(idproducto, productoActual);

            return ResponseEntity.status(HttpStatus.OK).body(productoActualizado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
