package com.practica.apidrogueria.models.dao;

import com.practica.apidrogueria.models.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


public interface IFacturaDao extends JpaRepository<Factura, Long> {

    @Transactional(readOnly = true)
    List<Factura> findFacturaByFechaCreacion(Date fecha_creacion);




}
