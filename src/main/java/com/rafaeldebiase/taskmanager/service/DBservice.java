package com.rafaeldebiase.taskmanager.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafaeldebiase.taskmanager.domain.Tarefa;
import com.rafaeldebiase.taskmanager.repository.TarefaRepository;

/**
 * @author casa
 *
 */
@Service
public class DBservice {
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	public void instantiateTestDatabase() {
		
		Tarefa t1 = new Tarefa(null, "Estudar", "java", false);
		Tarefa t2 = new Tarefa(null, "Estudar", "C#", false);
		Tarefa t3 = new Tarefa(null, "Corre", "praia a noite", true);
		Tarefa t4 = new Tarefa(null, "lanchar", "pizza", false);
		
		tarefaRepository.saveAll(Arrays.asList(t1, t2, t3, t4));
	}

}