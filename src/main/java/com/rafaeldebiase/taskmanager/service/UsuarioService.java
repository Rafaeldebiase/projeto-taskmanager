package com.rafaeldebiase.taskmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rafaeldebiase.taskmanager.domain.Tarefa;
import com.rafaeldebiase.taskmanager.domain.Usuario;
import com.rafaeldebiase.taskmanager.domain.enums.PerfilUsuario;
import com.rafaeldebiase.taskmanager.dto.UsuarioDto;
import com.rafaeldebiase.taskmanager.dto.UsuarioNewDto;
import com.rafaeldebiase.taskmanager.repository.UsuarioRepository;
import com.rafaeldebiase.taskmanager.security.UserSS;
import com.rafaeldebiase.taskmanager.service.exception.AuthorizationException;
import com.rafaeldebiase.taskmanager.service.exception.DataIngretyException;
import com.rafaeldebiase.taskmanager.service.exception.ObjectNotFoundException;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	

	public Usuario find(Integer id) {
		
		UserSS user = UserServices.authenticated();
		if ( user == null || !user.hasRole(PerfilUsuario.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Usuario> obj = repository.findById(id);
		return obj.orElseThrow(() -> 
		new ObjectNotFoundException("Objeto não encontrado id: " + id 
				+ "Tipo: " + Tarefa.class.getName()));
	}

	public Page<Usuario> findPage(
			Integer page, Integer linesPerPege, String oderBy, String direction) {
		
		UserSS user = UserServices.authenticated();
		if ( user == null ) {
			throw new AuthorizationException("Acesso negado");
		}
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPege, Direction.valueOf(direction), oderBy);
		return repository.findAll(pageRequest);
	}

	@Transactional
	public Usuario insert(Usuario obj) {
		obj.setId(null);
		obj = repository.save(obj);
		return obj;
	}

	public Usuario update(Usuario obj) {
		Usuario newObj = find(obj.getId());
		updateData(newObj, obj);
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
	
	public Usuario findByEmail(String email) {			
		return repository.findByEmail(email);
	}

	public List<Usuario> findAll() {			
		return repository.findAll();
	}
	
	private void updateData(Usuario newObj, Usuario obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		newObj.setSenha(obj.getSenha());
	}

	public Usuario fromDto(UsuarioDto objDto) {
		return new Usuario(objDto.getId(), objDto.getNome(), objDto.getEmail(), null);
	}
	
	public Usuario fromDto(UsuarioNewDto objDto) {
		return new Usuario(null, objDto.getNome(), objDto.getEmail(), pe.encode(objDto.getSenha()));
	}
	
	// 

}
