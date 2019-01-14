package com.rafaeldebiase.taskmanager.service.exception;

/**
 * @author Rafael de Biase
 *
 */
public class ObjectNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ObjectNotFoundException(String msn) {
		super(msn);
	}
	
	public ObjectNotFoundException(String msn, Throwable cause) {
		super(msn, cause);
	}

}