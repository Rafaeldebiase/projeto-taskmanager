package com.rafaeldebiase.taskmanager.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rafaeldebiase.taskmanager.domain.Tarefa;
import com.rafaeldebiase.taskmanager.domain.Usuario;
import com.rafaeldebiase.taskmanager.domain.enums.PerfilUsuario;
import com.rafaeldebiase.taskmanager.repository.TarefaRepository;
import com.rafaeldebiase.taskmanager.repository.UsuarioRepository;

/**
 * @author casa
 *
 */
@Service
public class DBservice {

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private TarefaRepository tarefaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void instantiateTestDatabase() {

		Tarefa t1 = new Tarefa(null, "Estudar", "java", false);
		Tarefa t2 = new Tarefa(null, "Estudar", "C#", false);
		Tarefa t3 = new Tarefa(null, "Corre", "praia a noite", true);
		Tarefa t4 = new Tarefa(null, "lanchar", "pizza", false);

		tarefaRepository.saveAll(Arrays.asList(t1, t2, t3, t4));

		Usuario u1 = new Usuario(null, "Rafael", "rafael@teste.com", pe.encode("123456"));
		u1.addPerfil(PerfilUsuario.ADMIN);

		u1.getTarefas().addAll(Arrays.asList(t3, t4));

		Usuario u2 = new Usuario(null, "Johnny", "Johnny@teste.com", pe.encode("123456"));

		u2.getTarefas().addAll(Arrays.asList(t1, t2));

		usuarioRepository.saveAll(Arrays.asList(u1));

	}

}