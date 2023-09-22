package com.practica.apidrogueria.services;

import com.practica.apidrogueria.models.entity.Factura;
import com.practica.apidrogueria.models.entity.Producto;

import java.util.Date;
import java.util.List;

public interface IService {

    public List<Producto> listarProductos();

    public Producto findProductoById(Long id);

    public Producto findProductoByNombre(String nombre);

    public List<Producto> findProductoByPrecio(Integer precio);

    public void saveProducto(Producto producto);

    public void actualizar(Long id, Producto producto);

    public void deleteProducto(Long id);

    public void deleteProductoByNombre(String nombre);

    public List<Producto> productos_a_vencer();

    public List<Factura> findFacturaByfechaCreacion(Date fecha);

    public void saveFactura(Factura factura);


    public Factura findFacturaById(Long id);

    public void deleteFactura(Long id);

}
