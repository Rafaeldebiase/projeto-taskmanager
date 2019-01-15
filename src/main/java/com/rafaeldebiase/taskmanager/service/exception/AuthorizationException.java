package com.rafaeldebiase.taskmanager.service.exception;

/**
 * @author Rafael de Biase
 *
 */
public class AuthorizationException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public AuthorizationException(String msn) {
		super(msn);
	}
	
	public AuthorizationException(String msn, Throwable cause) {
		super(msn, cause);
	}
}
