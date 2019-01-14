package com.rafaeldebiase.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rafaeldebiase.taskmanager.domain.Tarefa;

/**
 * @author Rafael de Biase
 *
 */

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {
	

}