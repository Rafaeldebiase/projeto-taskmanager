package com.rafaeldebiase.taskmanager.resource.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;

	private List<FieldMessage> Erros = new ArrayList<>();
	
	public ValidationError(Integer status, String msn, Long timeStamp) {
		super(status, msn, timeStamp);
	}

	public List<FieldMessage> getErros() {
		return Erros;
	}

	public void addError(String fieldName, String message) {
		Erros.add(new FieldMessage(fieldName, message));
	}

}
