package com.rafaeldebiase.taskmanager.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rafaeldebiase.taskmanager.domain.Tarefa;
import com.rafaeldebiase.taskmanager.domain.Usuario;
import com.rafaeldebiase.taskmanager.repository.TarefaRepository;
import com.rafaeldebiase.taskmanager.repository.UsuarioRepository;
import com.rafaeldebiase.taskmanager.security.UserSS;
import com.rafaeldebiase.taskmanager.service.exception.AuthorizationException;
import com.rafaeldebiase.taskmanager.service.exception.DataIngretyException;
import com.rafaeldebiase.taskmanager.service.exception.ObjectNotFoundException;


@Service
public class TarefaService {
	
	@Autowired
	private TarefaRepository repository;
		
	@Autowired
	private UsuarioService usuarioService;


	public Tarefa find(Integer id) {
		Optional<Tarefa> obj = repository.findById(id);
		return obj.orElseThrow(() -> 
		new ObjectNotFoundException("Objeto não encontrado id: " + id 
				+ "Tipo: " + Tarefa.class.getName()));
	}

	public Tarefa insert(Tarefa obj) {
		obj.setId(null);
		return repository.save(obj);
	}

	public Tarefa update(Tarefa obj) {
		find(obj.getId());
		return repository.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		try { 
			repository.deleteById(id);
		} catch (DataIngretyException e) {
			throw new DataIngretyException("Não é possível excluir uma categoria que possuí produtos");
		}
	}

	public Page<Tarefa> findPage(Integer page, Integer linesPerPege, String oderBy, String direction) {
		UserSS user = UserServices.authenticated();
		if ( user == null ) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPege, Direction.valueOf(direction), oderBy);
		Usuario usuario = usuarioService.find(user.getId());
		return repository.findByUsuario(usuario, pageRequest);
	}
	
	

}