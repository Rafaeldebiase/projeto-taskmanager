package com.rafaeldebiase.taskmanager.dto;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;

import javax.validation.constraints.NotEmpty;

import com.rafaeldebiase.taskmanager.domain.Tarefa;
import com.rafaeldebiase.taskmanager.domain.enums.StatusTarefa;

public class TarefaDto implements Serializable {

	private static final long serialVersionUID = 1L; 

	private Integer id;
	
	@NotEmpty(message="Preenchimento Obrigatório")
	private String titulo;
	private String descricao;
	private Boolean concluido;
	private Calendar dataCriacao;
	private Calendar dataPrevisaoConclusao;
	private StatusTarefa status;
	

	public TarefaDto() {
	}

	public TarefaDto(Tarefa obj) throws ParseException {
		id = obj.getId();
		titulo = obj.getTitulo();
		descricao = obj.getDescricao();
		concluido = obj.getConcluido();
		dataCriacao = obj.getDataCriacao();
		dataPrevisaoConclusao = obj.getDataPrevisaoConclusao();
		setStatus(obj.getStatus());
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Boolean getConcluido() {
		return concluido;
	}

	public void setConcluido(Boolean concluido) {
		this.concluido = concluido;
	}
	
	public Calendar getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Calendar dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Calendar getDataPrevisaoConclusao() {
		return dataPrevisaoConclusao;
	}

	public void setDataPrevisaoConclusao(Calendar dataPrevisaoConclusao) {
		this.dataPrevisaoConclusao = dataPrevisaoConclusao;
	}

	public StatusTarefa getStatus() {
		return status;
	}

	public void setStatus(StatusTarefa status) {
		this.status = status;
	}

}