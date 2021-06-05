package com.example.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.cursomc.domain.Categoria;
import com.example.cursomc.dto.CategoriaDTO;
import com.example.cursomc.repositories.CategoriaRepository;
import com.example.cursomc.services.exceptions.DataIntegrityException;
import com.example.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public List<Categoria> listar() {
		return repo.findAll();
	}
	
	public Categoria inserir(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! ID: " + id + ", Tipo: " + Categoria.class.getName()
		));
	}
	
	public Categoria atualizar(Categoria obj) {
		Categoria cat = buscar(obj.getId());
		
		atualizarDados(cat, obj);
		
		return repo.save(cat);
	}
	
	public void apagar(Integer id) {
		buscar(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException(
				"Não é possível excluir uma categoria que possui produtos"
			);
		}
	}

	public Page<Categoria> listarPaginado(Integer pagina, Integer quantidade, String ordenarPor, String direcao) {
		PageRequest pageRequest = PageRequest.of(pagina, quantidade, Direction.valueOf(direcao), ordenarPor);
		
		return repo.findAll(pageRequest);
	}
	
	public Categoria fromDto(CategoriaDTO obj) {
		return new Categoria(obj.getId(), obj.getNome());
	}
	
	private void atualizarDados(Categoria novo, Categoria requisicao) {
		novo.setNome(requisicao.getNome());
	}
}
