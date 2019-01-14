package com.rafaeldebiase.taskmanager.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OneToMany;

public class Usuario {
	
	private Integer id;
	private String nome;
	private String email;
	
	@OneToMany(mappedBy="tarefa")
	private List<Tarefa> tarefas = new ArrayList<>();
	
	public Usuario() {
	}

	public Usuario(Integer id, String nome, String email, List<Tarefa> tarefas) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.tarefas = tarefas;
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
