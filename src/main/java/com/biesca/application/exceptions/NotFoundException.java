package com.biesca.application.exceptions;

import org.springframework.stereotype.Component;

@Component
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2034428008684568596L;

	public NotFoundException() {
	super();
    }

    public NotFoundException(String message) {
	super(message);
    }

}
