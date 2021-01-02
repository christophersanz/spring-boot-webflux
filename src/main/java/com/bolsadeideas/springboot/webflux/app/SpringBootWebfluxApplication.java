package com.bolsadeideas.springboot.webflux.app;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.bolsadeideas.springboot.webflux.app.models.dao.ProductoDao;
import com.bolsadeideas.springboot.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringBootWebfluxApplication.class);
	
	@Autowired
	private ProductoDao dao;
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("productos").subscribe();
		List<Producto> lista = Arrays.asList(
			new Producto("TV Panasonic Pantalla LCD", 456.89),
			new Producto("Sony Camara HD Digital", 177.89),
			new Producto("Apple ipod", 46.89),
			new Producto("Sony Notebook", 846.89),
			new Producto("Hewlett Packard Multifuncional", 200.89),
			new Producto("Bianchi Bicicleta", 70.89),
			new Producto("HP Notebook Omen 17", 2500.89),
			new Producto("Mica Cómoda 5 cajones", 150.89),
			new Producto("TV Sony Bravia OLED 4K Ultra HD", 255.89)
			);
		
		Flux.fromIterable(lista)
		.flatMap(producto -> {
			producto.setCreateAt(new Date());
			return dao.save(producto);
			})
		.subscribe(producto -> log.info("Insert: " + producto.getId() + " " + producto.getNombre()));
	}

}
