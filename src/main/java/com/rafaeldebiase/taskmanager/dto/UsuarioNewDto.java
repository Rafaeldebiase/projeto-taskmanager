package com.rafaeldebiase.taskmanager.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.rafaeldebiase.taskmanager.domain.Usuario;
import com.rafaeldebiase.taskmanager.service.validation.UsuarioInsert;

@UsuarioInsert
public class UsuarioNewDto implements Serializable {

	private static final long serialVersionUID = 1L; 

	private Integer id;
	
	@NotEmpty(message="Preenchimento Obrigatório")
	@Length(min=5, max=80, message="O tamanho do nome deve ser entre 5 e 80 caracteres")
	private String nome;
	private String email;
	
	@NotEmpty(message="Preenchimento Obrigatório")
	private String senha;

	public UsuarioNewDto() {
	}

	public UsuarioNewDto(Usuario obj) {
		id = obj.getId();
		nome = obj.getNome();
		setEmail(obj.getEmail());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
