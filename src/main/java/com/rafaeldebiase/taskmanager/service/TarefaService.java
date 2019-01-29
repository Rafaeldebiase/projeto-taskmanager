package com.rafaeldebiase.taskmanager.service;

import java.text.ParseException;
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
import com.rafaeldebiase.taskmanager.tools.Useful;


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
	
	public List<Tarefa> findByUsuario(Usuario id) {
		return repository.findByUsuario(id);
	}
	
	public List<Tarefa> findAll() {		
		return repository.findAll();
	}
	
	public Tarefa insert(Tarefa obj) {
		obj.setId(null);
		obj.setDataCriacao(Calendar.getInstance());
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
		obj.setDataCriacao(newObj.getDataCriacao());
		obj.setUsuario(newObj.getUsuario());	
	}
	
	public Tarefa fromDto(TarefaDto objDto) throws ParseException {
		StatusTarefa newObj = Useful.verrifyStatusTarefa(objDto);
		
		Calendar dataConvertida = Useful.convertsStringForCalendar(objDto.getDataPrevisaoConclusao());
		
		
		return new Tarefa(null, objDto.getTitulo(), objDto.getDescricao(), objDto.getConcluido(), objDto.getDataCriacao(), 
				dataConvertida, newObj, null);
		
	}

	public Tarefa fromDto(TarefaNewDto objDto) throws ParseException {
		StatusTarefa newObj = Useful.verrifyStatusTarefa(objDto);
		
		Calendar newDate = Useful.convertsStringForCalendar(objDto.getDataPrevisaoEntrega());
		
		return new Tarefa(null, objDto.getTitulo(), objDto.getDescricao(), objDto.getConcluido(), Calendar.getInstance(), newDate, 
				newObj, usuarioService.find(objDto.getIdUsuario()));
	}
	
	
	
	
	
}