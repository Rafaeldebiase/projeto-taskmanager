package com.rafaeldebiase.taskmanager.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rafaeldebiase.taskmanager.domain.Tarefa;
import com.rafaeldebiase.taskmanager.domain.Usuario;

/**
 * @author Rafael de Biase
 *
 */

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {
	
	@Transactional(readOnly=true)
	Page<Tarefa> findByUsuario(Usuario usuario, Pageable pageRequest);
	
	@Transactional(readOnly=true)
	List<Tarefa> findByUsuario(Usuario id);
	
}