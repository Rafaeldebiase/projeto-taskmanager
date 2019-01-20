package com.rafaeldebiase.taskmanager.resource;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rafaeldebiase.taskmanager.domain.Tarefa;
import com.rafaeldebiase.taskmanager.domain.Usuario;
import com.rafaeldebiase.taskmanager.dto.TarefaDto;
import com.rafaeldebiase.taskmanager.dto.TarefaNewDto;
import com.rafaeldebiase.taskmanager.service.TarefaService;

/**
 * @author rafael de Biase
 *
 */

@RestController
@RequestMapping(value="/tarefas")
public class TarefaResource {

	@Autowired
	private TarefaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Tarefa obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<TarefaDto>> findAll() {
		List<Tarefa> list = service.findAll();
		List<TarefaDto> listTDO = list.stream()
				.map(obj -> new TarefaDto(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listTDO);
	}
	
	@PreAuthorize("hasAnyRole('USUARIO')")
	@RequestMapping(value="/usuario={id}", method=RequestMethod.GET)
	public ResponseEntity<List<TarefaDto>> findByUsuario(@PathVariable Usuario id) {
		List<Tarefa> list = service.findByUsuario(id);
		List<TarefaDto> listTDO = list.stream()
				.map(obj -> new TarefaDto(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listTDO);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<Tarefa>> page(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPege", defaultValue="24") Integer linesPerPege, 
			@RequestParam(value="oderBy", defaultValue="titulo") String oderBy, 
			@RequestParam(value="direction", defaultValue="DESC") String direction) {
		Page<Tarefa> list = service.findPage(page, linesPerPege, oderBy, direction);
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody TarefaNewDto objDto){
		Tarefa obj = service.fromDto(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody TarefaNewDto objDto, @PathVariable Integer id) {
		Tarefa obj = service.fromDto(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
