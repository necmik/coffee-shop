package com.coffeetime.coffeeshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class HttpEmptyOrderException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public HttpEmptyOrderException(String message) {
        super(message);
    }

    public HttpEmptyOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
