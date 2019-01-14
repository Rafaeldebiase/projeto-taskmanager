package com.rafaeldebiase.taskmanager.resource.exception;
import java.io.Serializable;

/**
 * @author Rafael de Biase
 *
 */
public class StandardError implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer Status;
	private String Msn;
	private Long TimeStamp;
	
	public StandardError(Integer status, String msn, Long timeStamp) {
		super();
		Status = status;
		Msn = msn;
		TimeStamp = timeStamp;
	}

	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer status) {
		Status = status;
	}

	public String getMsn() {
		return Msn;
	}

	public void setMsn(String msn) {
		Msn = msn;
	}

	public Long getTimeStamp() {
		return TimeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		TimeStamp = timeStamp;
	}

}