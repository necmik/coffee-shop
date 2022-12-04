package com.coffeetime.coffeeshop.exception;

public class HttpEmptyOrderException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public HttpEmptyOrderException(String message) {
        super(message);
    }

    public HttpEmptyOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
