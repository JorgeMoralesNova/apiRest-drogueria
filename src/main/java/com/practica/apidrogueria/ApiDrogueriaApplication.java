package com.practica.apidrogueria;

import com.practica.apidrogueria.models.entity.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;



@SpringBootApplication
public class ApiDrogueriaApplication {


    public static void main(String[] args) {
        SpringApplication.run(ApiDrogueriaApplication.class, args);
    }


}
