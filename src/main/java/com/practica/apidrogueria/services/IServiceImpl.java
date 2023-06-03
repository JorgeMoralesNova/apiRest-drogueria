package com.practica.apidrogueria.services;

import com.practica.apidrogueria.models.dao.IFacturaDao;
import com.practica.apidrogueria.models.dao.IProductoDao;
import com.practica.apidrogueria.models.entity.Factura;
import com.practica.apidrogueria.models.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class IServiceImpl implements IService{

    @Autowired
    IFacturaDao facturaDao;
    @Autowired
    IProductoDao productoDao;

    @Override
    public List<Producto> listarProductos() {
        return productoDao.findAll();
    }

    @Override
    public List<Producto> findProductoByNombre(String nombre) {
        return productoDao.findProductoByNombreLikeIgnoreCase(nombre);
    }

    @Override
    public List<Producto> findProductoByPrecio(Integer precio) {
        return productoDao.findProductoByPrecio(precio);
    }

    @Override
    public List<Factura> findFacturaByfechaCreacion(Date fecha) {
        return facturaDao.findFacturaByFechaCreacion(fecha);
    }


    @Override
    public void saveFactura(Factura factura) {
        facturaDao.save(factura);
    }

    @Override
    public void saveProducto(Producto producto) {

        productoDao.save(producto);
    }

    @Override
    public Producto findProductoById(Long id) {
        return productoDao.findById(id).orElse(null);
    }

    @Override
    public Factura findFacturaById(Long id) {
        return facturaDao.findById(id).orElse(null);
    }

    @Override
    public void deleteFactura(Long id) {
        facturaDao.deleteById(id);
    }

    @Override
    public void deleteProducto(Long id) {
        productoDao.deleteById(id);
    }

    @Override
    public void deleteProductoByNombre(String nombre) {
        productoDao.deleteProductoByNombre(nombre);
    }


    //Productos a vencer:

    @Override
    public List<Producto> productos_a_vencer(){
        Date actual= new Date();

        List<Producto> productos=listarProductos();

        List<Producto> productos_a_vencer= new ArrayList<Producto>();

        if (productos!=null){

            for (Producto producto: productos){

                Date fechaVencimiento = producto.getFecha_vencimiento();

                if (fechaVencimiento != null) {
                    long tiempoRestante = fechaVencimiento.getTime() - actual.getTime();
                    long tiempoMaximoUnAnio = 365L * 24 * 60 * 60 * 1000; // 1 año en milisegundos

                    if (tiempoRestante <= 0 || tiempoRestante <= tiempoMaximoUnAnio) {
                        productos_a_vencer.add(producto);
                    }
                }
            }
        }

        return productos_a_vencer;


    }

    @Override
    public void actualizar(Long id, Producto producto){

        Producto actual=findProductoById(id);

        actual.setNombre(producto.getNombre());
        actual.setDescripcion(producto.getDescripcion());
        actual.setPrecio(producto.getPrecio());
        actual.setFecha_vencimiento(producto.getFecha_vencimiento());

        productoDao.save(actual);

    }




}
