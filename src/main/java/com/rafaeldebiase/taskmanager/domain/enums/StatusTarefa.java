package com.rafaeldebiase.taskmanager.domain.enums;

public enum StatusTarefa {
	
	PENDENTE(1, "ROLE_USUARIO"),
	ATRASADA(2, "ROLE_USUARIO");
	
	private int cod;
	private String descricao;
	
	private StatusTarefa(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static StatusTarefa toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		
		for (StatusTarefa x: StatusTarefa.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	

}
