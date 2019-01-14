package com.rafaeldebiase.taskmanager.service.exception;

/**
 * @author Rafael de Biase
 *
 */
public class DataIngretyException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public DataIngretyException(String msn) {
		super(msn);
	}
	
	public DataIngretyException(String msn, Throwable cause) {
		super(msn, cause);
	}
}
