package com.example.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import com.example.cursomc.domain.Cliente;
import com.example.cursomc.dto.ClienteDTO;
import com.example.cursomc.repositories.ClienteRepository;
import com.example.cursomc.services.exceptions.DataIntegrityException;
import com.example.cursomc.services.exceptions.ObjectNotFoundException;

public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public List<Cliente> listar() {
		return repo.findAll();
	}
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! ID: " + id + ", Tipo: " + Cliente.class.getName()
		));
	}
	
	public Cliente atualizar(Cliente obj) {
		Cliente cliente = buscar(obj.getId());
		
		atualizarDados(cliente, obj);
		
		return repo.save(cliente);
	}
	
	public void apagar(Integer id) {
		buscar(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException(
				"Não é possível excluir porque há entidades relacionadas"
			);
		}
	}

	public Page<Cliente> listarPaginado(Integer pagina, Integer quantidade, String ordenarPor, String direcao) {
		PageRequest pageRequest = PageRequest.of(pagina, quantidade, Direction.valueOf(direcao), ordenarPor);
		
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDto(ClienteDTO obj) {
		return new Cliente(obj.getId(), obj.getNome(), obj.getEmail(), null, null);
	}
	
	private void atualizarDados(Cliente novo, Cliente requisicao) {
		novo.setNome(requisicao.getNome());
		novo.setEmail(requisicao.getEmail());
	}

}
