package com.pikolinc.meliecommerce.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a requested resource cannot be found in the system.
 * <p>
 * This exception typically indicates that a database entity, file, or
 * other resource was not located given its identifier or lookup parameters.
 * It results in an HTTP {@code 404 Not Found} response.
 * </p>
 *
 * <p><strong>Usage example:</strong></p>
 * <pre>{@code
 * throw new NotFoundException("Client not found with id " + id);
 * }</pre>
 *
 * @see HttpStatus#NOT_FOUND
 * @see ResponseException
 */
public class NotFoundException extends ResponseException {

    /**
     * Constructs a new {@code NotFoundException} with the specified detail message.
     *
     * @param message a descriptive message explaining which resource was not found
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
