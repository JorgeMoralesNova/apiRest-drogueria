package com.practica.apidrogueria.controllers;


import com.practica.apidrogueria.models.entity.Producto;
import com.practica.apidrogueria.services.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RequestMapping(value = "/api-drogueria/catalogo")
@org.springframework.web.bind.annotation.RestController
public class ProductoRestController {

    @Autowired
    IService service;

    @GetMapping("/productos")
    public List<Producto> catalogo(){

        return service.listarProductos();
    }

    @GetMapping("/productos/{id}")
    public Producto productoPorId(@PathVariable Long id){

        return service.findProductoById(id);
    }

    @GetMapping("/productos/{nombre}")
    public List<Producto> productoPorNombre(@PathVariable String nombre){

        return service.findProductoByNombre(nombre);
    }


    @GetMapping("/PorPrecio/{precio}")
    public List<Producto> productosPorPrecio(@PathVariable Double precio){

        return service.findProductoByPrecio(precio);

    }


    @GetMapping("/FechaVencimientoCorta")
    public List<Producto> fechaVencimientoCorta(){

        return service.productos_a_vencer();
    }

    @PostMapping("/productos")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto guardarProducto(@RequestBody Producto producto){

        service.saveProducto(producto);

        return producto;
    }

    @PutMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto actualizarProducto(@PathVariable Long id, @RequestBody Producto producto){

        service.actualizar(id, producto);

        return producto;
    }

    @DeleteMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id){

        service.deleteProducto(id);
    }

    @DeleteMapping("/clientes/{nombre}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarPorNombre(@PathVariable String nombre){

        service.deleteProductoByNombre(nombre);

    }


}
