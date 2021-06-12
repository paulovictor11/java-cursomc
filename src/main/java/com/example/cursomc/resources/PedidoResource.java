package com.example.cursomc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.cursomc.domain.Categoria;
import com.example.cursomc.domain.Pedido;
import com.example.cursomc.dto.CategoriaDTO;
import com.example.cursomc.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService service;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> inserir(@Valid @RequestBody Pedido obj) {
		obj = service.inserir(obj);
		URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(obj.getId())
					.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable Integer id) {
		Pedido obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
}
