package com.practica.apidrogueria.models.dao;

import com.practica.apidrogueria.models.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


public interface IProductoDao extends JpaRepository<Producto,Long> {


    @Transactional(readOnly = true)
    public List<Producto> findProductoByNombreLikeIgnoreCase(String nombre);

    @Transactional(readOnly = true)
    public List<Producto> findProductoByFecha_vencimiento(Date fecha_vencimiento);

    @Transactional(readOnly = true)
    public List<Producto> findProductoByPrecio(Double precio);

    @Transactional
    public void deleteProductoByNombre(String nombre);
}
