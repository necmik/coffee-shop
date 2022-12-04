package com.coffeetime.coffeeshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class HttpNotFoundErrorException extends HttpClientErrorException {

	private static final long serialVersionUID = 1L;

	public HttpNotFoundErrorException(String message) {
        super(message);
    }

    public HttpNotFoundErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}