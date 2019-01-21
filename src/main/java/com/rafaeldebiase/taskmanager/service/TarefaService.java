package com.rafaeldebiase.taskmanager.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rafaeldebiase.taskmanager.domain.Tarefa;
import com.rafaeldebiase.taskmanager.domain.Usuario;
import com.rafaeldebiase.taskmanager.domain.enums.StatusTarefa;
import com.rafaeldebiase.taskmanager.dto.TarefaDto;
import com.rafaeldebiase.taskmanager.dto.TarefaNewDto;
import com.rafaeldebiase.taskmanager.repository.TarefaRepository;
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
		UserSS user = UserServices.authenticated();
		if ( user == null ) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Tarefa> obj = repository.findById(id);
		return obj.orElseThrow(() -> 
		new ObjectNotFoundException("Objeto não encontrado id: " + id 
				+ "Tipo: " + Tarefa.class.getName()));
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
	
	public List<Tarefa> findByUsuario(Usuario email) {
		return repository.findByUsuario(email);
	}
	
	public List<Tarefa> findAll() {		
		return repository.findAll();
	}
	
	public Tarefa insert(Tarefa obj) {
		obj.setId(null);
		return repository.save(obj);
	}

	public Tarefa update(Tarefa obj) {
		Tarefa newObj = find(obj.getId());
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
	
	private void updateData(Tarefa newObj, Tarefa obj) {
		newObj.setTitulo(obj.getTitulo());
		newObj.setDescricao(obj.getDescricao());
		newObj.setConcluido(obj.getConcluido());
	}
	
	public Tarefa fromDto(TarefaDto objDto) {
		StatusTarefa newObj = this._verificaStatusTarefa(objDto);
		return new Tarefa(null, objDto.getTitulo(), objDto.getDescricao(), objDto.getConcluido(), null, objDto.getDataPrevisaoConclusao(), newObj, null);
		
	}

	public Tarefa fromDto(TarefaNewDto objDto) {
		StatusTarefa newObj = this._verificaStatusTarefa(objDto);
		return new Tarefa(null, objDto.getTitulo(), objDto.getDescricao(), objDto.getConcluido(), Calendar.getInstance(), objDto.getDataPrevisaoEntrega(), newObj, usuarioService.find(objDto.getIdUsuario()));
	}
	
	private StatusTarefa _verificaStatusTarefa(TarefaNewDto objDto) {
		if (objDto.getDataPrevisaoEntrega().before(Calendar.getInstance()) && !objDto.getConcluido()) {
			return StatusTarefa.PENDENTE;
		} else if (objDto.getDataPrevisaoEntrega().equals(Calendar.getInstance()) && !objDto.getConcluido()) {
			return StatusTarefa.PENDENTE;
		} else if (objDto.getDataPrevisaoEntrega().after(Calendar.getInstance()) && !objDto.getConcluido()) {
			return StatusTarefa.ATRASADA;
		} else 
		return StatusTarefa.CONCLUIDO;
	}
	
	private StatusTarefa _verificaStatusTarefa(TarefaDto objDto) {
		if (objDto.getDataPrevisaoConclusao().before(Calendar.getInstance()) && !objDto.getConcluido()) {
			return StatusTarefa.PENDENTE;
		} else if (objDto.getDataPrevisaoConclusao().equals(Calendar.getInstance()) && !objDto.getConcluido()) {
			return StatusTarefa.PENDENTE;
		} else if (objDto.getDataPrevisaoConclusao().after(Calendar.getInstance()) && !objDto.getConcluido()) {
			return StatusTarefa.ATRASADA;
		} else 
		return StatusTarefa.CONCLUIDO;
	}
	
}