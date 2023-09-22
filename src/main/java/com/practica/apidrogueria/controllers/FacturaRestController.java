package com.practica.apidrogueria.controllers;

import com.practica.apidrogueria.models.entity.Factura;
import com.practica.apidrogueria.services.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import java.util.Map;

@RequestMapping("/facturas")
@RestController
public class FacturaRestController {

    @Autowired
    IService service;
    @GetMapping("/{id}")
    public ResponseEntity<?> factura(@PathVariable Long id){

        Factura factura=null;
        Map<String,Object> response= new HashMap<>();

        try {
            factura=service.findFacturaById(id);
        }catch (DataAccessException e){
            response.put("mensaje", "ha ocurrido un error al hacer la consulta al servidor");
            response.put("error", e.getMessage().concat(" ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(factura==null){
            response.put("mensaje", "factura no encontrada en sistema");

        }


        return new ResponseEntity<Factura>(factura, HttpStatus.FOUND);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFactura(@PathVariable Long id){

        Map<String,Object> map=new HashMap<>();

        try {
            service.deleteFactura(id);

        }catch (DataAccessException e){

            map.put("mensaje", "Error al eliminar la factura en la base de datos");
            map.put("error", e.getMessage().concat(" : ".concat(e.getMostSpecificCause().getMessage())));

        }

        map.put("mensaje", "Factura elimina con exito");

        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NO_CONTENT);

        }


}
