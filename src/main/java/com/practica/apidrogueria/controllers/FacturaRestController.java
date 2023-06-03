package com.practica.apidrogueria.controllers;

import com.practica.apidrogueria.models.entity.Factura;
import com.practica.apidrogueria.services.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
