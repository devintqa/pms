package com.psk.exception;

/**
 * Created by Sony on 08-10-2015.
 */
public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
