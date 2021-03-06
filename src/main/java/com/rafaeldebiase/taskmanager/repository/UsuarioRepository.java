/**
 * 
 */
package com.rafaeldebiase.taskmanager.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rafaeldebiase.taskmanager.domain.Usuario;

/**
 * @author Rafael de Biase
 *
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	@Transactional(readOnly=true)
	Usuario findByEmail(String email);
	
}
