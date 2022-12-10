package com.coffeetime.coffeeshop.exception;

public class HttpProductNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public HttpProductNotFoundException(String message) {
        super(message);
    }

    public HttpProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
