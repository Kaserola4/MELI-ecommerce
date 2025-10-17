package com.pikolinc.meliecommerce.exception;

import org.springframework.http.HttpStatus;

public abstract class ResponseException extends RuntimeException {
    /**
     * Constructs a new ResponseException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public ResponseException(String message) {
        super(message);
    }

    /**
     * Returns the HTTP status code associated with this exception.
     * <p>
     * This method must be implemented by each concrete subclass to define
     * the appropriate HTTP status code that should be returned to the client
     * when this exception is thrown.
     * </p>
     *
     * @return the HTTP status code for this exception
     */
    public abstract HttpStatus getHttpStatus();
}
