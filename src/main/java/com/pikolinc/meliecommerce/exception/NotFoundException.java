package com.pikolinc.meliecommerce.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ResponseException {
    /**
     * Constructs a new NotFoundException with the specified detail message.
     *
     * @param message the detail message, typically including the resource ID that was not found
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * Returns the HTTP status code for this exception.
     *
     * @return {@link HttpStatus#NOT_FOUND} (404)
     */
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
