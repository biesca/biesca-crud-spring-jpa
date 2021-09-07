package com.biesca.application.exceptions;

import org.springframework.stereotype.Component;

@Component
public class AlreadyExistsException extends RuntimeException {


	private static final long serialVersionUID = -6252044298011966024L;

	public AlreadyExistsException() {
	super();
    }

    public AlreadyExistsException(String message) {
	super(message);
    }

}
