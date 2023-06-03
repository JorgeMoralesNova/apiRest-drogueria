package com.practica.apidrogueria.controllers;

import com.practica.apidrogueria.models.entity.Factura;
import com.practica.apidrogueria.services.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/facturas")
@RestController
public class FacturaRestController {

    @Autowired
    IService service;
    @GetMapping("/{id}")
    public Factura factura(@RequestParam Long id){

        return service.findFacturaById(id);

    }

    @DeleteMapping("facturas/{id}")
    public void deleteFactura(@RequestParam Long id){

        service.deleteFactura(id);
    }


}
