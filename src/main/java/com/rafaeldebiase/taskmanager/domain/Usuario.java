package com.rafaeldebiase.taskmanager.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rafaeldebiase.taskmanager.domain.enums.PerfilUsuario;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	@Column(unique=true)
	
	private String email;
	
	@JsonIgnore
	private String senha;
	
	@JsonIgnore
	@OneToMany(mappedBy="usuario")
	private List<Tarefa> tarefas = new ArrayList<>();
	
	 @ElementCollection(fetch=FetchType.EAGER)
	 @CollectionTable(name="PERFIS")
	 private Set<Integer> perfis = new HashSet<>();
	
	public Usuario() {
		addPerfil(PerfilUsuario.USUARIO);
	}

	public Usuario(Integer id, String nome, String email, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		addPerfil(PerfilUsuario.USUARIO);
		
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
	
	public Set<PerfilUsuario> getPerfis() {
		return perfis.stream().map(x -> PerfilUsuario.toEnum(x))
				.collect(Collectors.toSet());
	}  
	
	public void addPerfil(PerfilUsuario perfil) {
		perfis.add(perfil.getCod());
	}
	
	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
