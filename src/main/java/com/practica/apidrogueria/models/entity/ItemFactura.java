package com.practica.apidrogueria.models.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "items_factura")
public class ItemFactura implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private Integer cantidad;


    public Double calcularImporte(){
        return cantidad.doubleValue()* producto.getPrecio();
    }


    public Long getId() {
        return id;
    }


    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    private static final long serialVersionUID = 42L;

}
