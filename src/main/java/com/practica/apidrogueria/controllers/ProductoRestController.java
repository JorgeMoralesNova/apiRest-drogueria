package com.practica.apidrogueria.controllers;
import com.practica.apidrogueria.models.entity.Producto;
import com.practica.apidrogueria.services.IService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RequestMapping(value = "/api-drogueria/catalogo")
@org.springframework.web.bind.annotation.RestController
public class ProductoRestController {

    @Autowired
    IService service;

    @GetMapping("/productos")
    public ResponseEntity<?> catalogo(){

        List<Producto> productos;

        try {
            productos= service.listarProductos();
        }catch (DataAccessException exception){
            return ResponseEntity.internalServerError().body(Collections.singletonMap("error",exception.getMessage()));
        }


        Map<String,Object> reponse= new HashMap<>();
        if (productos==null  || productos.isEmpty()){
            reponse.put("mensaje", "actualmente no hay registros en la base de datos");

            return new ResponseEntity<Map<String,Object>>(reponse,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<Producto>>(productos, HttpStatus.FOUND);
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<?> productoPorId(@PathVariable Long id){

        Producto producto=null;

        Map<String, Object> reponse=new HashMap<>();

        try {
            producto=service.findProductoById(id);
        }catch (DataAccessException exception){

            reponse.put("mensaje", "ha ocurrido un problema al realizar la consulta a la base de datos");
            reponse.put("error", exception.getMessage().concat(": ")
                    .concat(exception.getMostSpecificCause().getMessage()));

            return new ResponseEntity<Map<String,Object>>(reponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(producto==null){
            reponse.put("mensaje", "el usuario con id ".concat(id.toString())
                    .concat(" no esta registrado en la base de datos"));
            return new ResponseEntity<Map<String,Object>>(reponse, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Producto>(producto, HttpStatus.OK);
    }

    @GetMapping("/productos/nombre/{nombre}")
    public ResponseEntity<?> productoPorNombre(@PathVariable String nombre){

        Producto producto=null;
        Map<String, Object> reponse=new HashMap<>();

        try {
            producto=service.findProductoByNombre(nombre);
        }catch (DataAccessException accessException){
            reponse.put("mensaje", "Error en consulta a la base de datos");
            reponse.put("error",accessException.getMessage().concat(": ")
                    .concat(accessException.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(reponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(producto==null){
            reponse.put("mensaje", "producto no encontrado en sistema");
            return new ResponseEntity<Map<String,Object>>(reponse, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Producto>(producto, HttpStatus.FOUND);
    }


    @GetMapping("/PorPrecio/{precio}")
    public ResponseEntity<?> productosPorPrecio(@PathVariable Integer precio){

        List<Producto> productos=null;
        Map<String,Object> response= new HashMap<>();

        try {
            productos=service.findProductoByPrecio(precio);
        }catch (DataAccessException e){

            response.put("mensaje", "error de acceso a la base de datos");
            response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (productos==null || productos.isEmpty()){
            response.put("mensaje", "No hay productos con valor de ".concat(precio.toString()));

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<List<Producto>>(productos, HttpStatus.FOUND);

    }


    @GetMapping("/FechaVencimientoCorta")
    public ResponseEntity<?> fechaVencimientoCorta(){

        List<Producto> productos=new ArrayList<>();
        try {
            productos=service.productos_a_vencer();
        }catch (DataAccessException accessException){
            ResponseEntity.internalServerError().body(Collections.singletonMap("error", accessException.getMessage()));
        }

        Map<String,Object> reponse=new HashMap<>();
        if (productos==null || productos.isEmpty()){
            reponse.put("mensaje", "no hay productos a vencer en menos de 1 año");
            return new ResponseEntity<Map<String,Object>>(reponse, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
    }

    @PostMapping("/productos")
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> guardarProducto(@Valid @RequestBody Producto producto, BindingResult result){

        Map<String, Object> mensaje=new HashMap<>();
        if(result.hasErrors()){
            mensaje.put("mensaje", "el producto no cumple con formato valido");

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El '" + err.getField() +"': "+ err.getDefaultMessage())
                    .collect(Collectors.toList());

            mensaje.put("errors", errors);

            return new ResponseEntity<Map<String,Object>>(mensaje, HttpStatus.BAD_REQUEST);
        }
        try {
            service.saveProducto(producto);

        }catch (DataAccessException e){
            mensaje.put("mensaje", "Ha ocurrido un error en el servidor");
            mensaje.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String,Object>>(mensaje, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        mensaje.put("mensaje", "producto guardado exitosamente");
        mensaje.put("producto", producto);
        return new ResponseEntity<Map<String,Object>>(mensaje, HttpStatus.CREATED);
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id,@Valid @RequestBody Producto producto, BindingResult result){

        Map<String, Object> mensaje= new HashMap<>();

        if(result.hasErrors()){
            mensaje.put("mensaje", "el producto no cumple con formato valido");

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El '" + err.getField() +"': "+ err.getDefaultMessage())
                    .collect(Collectors.toList());

            mensaje.put("errors", errors);

            return new ResponseEntity<Map<String,Object>>(mensaje, HttpStatus.BAD_REQUEST);
        }

        try {
            service.actualizar(id, producto);
        }catch (DataAccessException e){
            mensaje.put("mensaje", "Ha ocurrido un error en el servidor");
            mensaje.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String,Object>>(mensaje, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        mensaje.put("mensaje", "producto actualizado con exito");
        mensaje.put("producto", producto);

        return new ResponseEntity<Map<String, Object>>(mensaje, HttpStatus.CREATED);
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){

        Map<String,Object> mensajes=new HashMap<>();

        try {
            service.deleteProducto(id);
        }catch (DataAccessException e){

            mensajes.put("mensaje","hay algun error al intertar borrar el producto en el sistema");

            mensajes.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));

            return new ResponseEntity<Map<String,Object>>(mensajes, HttpStatus.INTERNAL_SERVER_ERROR);
        }
            mensajes.put("mensaje","producto eliminado con exito");

        return new ResponseEntity<Map<String,Object>>(mensajes, HttpStatus.NO_CONTENT);
        }

    @DeleteMapping("/productos/nombre/{nombre}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> eliminarPorNombre(@PathVariable String nombre){
        Map<String,Object> mensajes=new HashMap<>();

        try {
            service.deleteProductoByNombre(nombre);
        }catch (DataAccessException e){

            mensajes.put("mensaje","hay algun error al intentar borrar el producto ".concat(nombre).concat(" del sistema"));

            mensajes.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));

            return new ResponseEntity<Map<String,Object>>(mensajes, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        mensajes.put("mensaje","producto ".concat(nombre.toUpperCase()).concat(" eliminado con exito"));

        return new ResponseEntity<Map<String,Object>>(mensajes, HttpStatus.NO_CONTENT);

    }


}
