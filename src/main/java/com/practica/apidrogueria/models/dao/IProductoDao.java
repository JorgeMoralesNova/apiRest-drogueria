package com.practica.apidrogueria.models.dao;

import com.practica.apidrogueria.models.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


public interface IProductoDao extends JpaRepository<Producto,Long> {


    @Transactional(readOnly = true)
    public Producto findProductoByNombreLikeIgnoreCase(String nombre);

    @Transactional(readOnly = true)
    public List<Producto> findProductoByPrecio(Integer precio);

    @Transactional
    public void deleteProductoByNombre(String nombre);
}
