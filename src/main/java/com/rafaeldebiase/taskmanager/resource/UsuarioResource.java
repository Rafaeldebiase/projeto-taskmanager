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

import com.rafaeldebiase.taskmanager.domain.Usuario;
import com.rafaeldebiase.taskmanager.dto.UsuarioDto;
import com.rafaeldebiase.taskmanager.dto.UsuarioNewDto;
import com.rafaeldebiase.taskmanager.service.UsuarioService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(value="/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuarioService service;
	
	@ApiOperation(value="Busca tarefa por Id")
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Usuario> find(@PathVariable Integer id) {
		Usuario obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PreAuthorize("hasAnyRole('USUARIO')")
	@ApiOperation(value="Busca tarefa por email")
	@RequestMapping(value="/email={email}", method=RequestMethod.GET)
	public ResponseEntity<Usuario> findByEmail(@PathVariable String email) {
		Usuario obj = service.findByEmail(email);
		return ResponseEntity.ok().body(obj);
	}
	
	@ApiOperation(value="Lista todas os usuários - restrição: Admin")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<UsuarioDto>> findAll() {
		List<Usuario> list = service.findAll();
		List<UsuarioDto> listDTO = list.stream()
				.map(obj -> new UsuarioDto(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@ApiOperation(value="Lista todas os usuários com paginação - restrição: Admin")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<UsuarioDto>> page(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPege", defaultValue="24") Integer linesPerPege, 
			@RequestParam(value="oderBy", defaultValue="nome") String oderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Usuario> list = service.findPage(page, linesPerPege, oderBy, direction);
		Page<UsuarioDto> listDTO = list.map(obj -> new UsuarioDto(obj));
		return ResponseEntity.ok().body(listDTO);
	}
	
	@ApiOperation(value="Insere novo usário.")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody UsuarioNewDto objDto){
		Usuario obj = service.fromDto(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation(value="Atualiza a usuário.")
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody UsuarioDto objDto, @PathVariable Integer id) {
		Usuario obj = service.fromDto(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value="Apaga o usuário - retritrição: Admin ")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
