package com.rafaeldebiase.taskmanager.domain;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Rafael de Biase
 *
 */

@Entity
public class Tarefa {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String titulo;
	private String descricao;
	private Boolean concluido;
	private Calendar dataCriacao;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	public Tarefa() {
	}

	/**
	 * @param id
	 * @param nome
	 * @param descricao
	 * @param concluido
	 */
	public Tarefa(Integer id, String nome, String descricao, Boolean concluido, Calendar dataCriacao,Usuario usuario) {
		super();
		this.id = id;
		this.titulo = nome;
		this.descricao = descricao;
		this.concluido = concluido;
		this.dataCriacao = dataCriacao;
		this.usuario = usuario;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the nome
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setTitulo(String nome) {
		this.titulo = nome;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the concluido
	 */
	public Boolean getConcluido() {
		return concluido;
	}

	/**
	 * @param concluido the concluido to set
	 */
	public void setConcluido(Boolean concluido) {
		this.concluido = concluido;
	}
	
	public Calendar getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Calendar dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tarefa other = (Tarefa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
