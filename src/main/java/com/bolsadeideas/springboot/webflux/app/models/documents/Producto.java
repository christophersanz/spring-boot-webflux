package com.bolsadeideas.springboot.webflux.app.models.documents;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection="productos")
@Getter @Setter
public class Producto {
	
	public Producto() {	}
	
	public Producto(String nombre, Double precio) {
		this.nombre = nombre;
		this.precio = precio;
	}
	
	@Id
	private String id;
	
	private String nombre;
	private Double precio;
	private Date createAt;

}
