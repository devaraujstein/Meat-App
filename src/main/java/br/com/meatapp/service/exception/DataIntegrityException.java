package br.com.meatapp.service.exception;

public class DataIntegrityException extends RuntimeException {
	public static final long serialVersionUID = 1L;
	
	public DataIntegrityException(String msg) {
		super(msg);
	}
}
