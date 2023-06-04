package com.practica.apidrogueria.models.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "facturas")
public class Factura implements Serializable {

    public Factura() {

        items= new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String descripcion;

    private String observacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;


    @PrePersist
    public void prePersist(){

        fechaCreacion= new Date();
    }


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "factura_id")
    private List<ItemFactura> items;


    private void addItem(ItemFactura itemFactura){

        items.add(itemFactura);

    }

    private Double getTotal(){

        Double total=0.0;

        for(ItemFactura itemFactura: items){

            total+=itemFactura.calcularImporte();
        }

        return total;
    }



    private static final long serialVersionUID = 42L;

}
