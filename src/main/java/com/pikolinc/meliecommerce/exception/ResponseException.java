package com.pikolinc.meliecommerce.exception;

import org.springframework.http.HttpStatus;

/**
 * Base abstract class for all custom exceptions that should be returned
 * as HTTP responses within the application.
 * <p>
 * Extending this class allows defining domain-specific exceptions that
 * carry both an error message and an associated {@link HttpStatus} code.
 * </p>
 *
 * <p><strong>Usage example:</strong></p>
 * <pre>{@code
 * throw new NotFoundException("Client not found with id " + id);
 * }</pre>
 *
 * <p><strong>Subclass responsibilities:</strong></p>
 * <ul>
 *     <li>Provide a constructor that calls {@link #ResponseException(String)} with a meaningful message.</li>
 *     <li>Override {@link #getHttpStatus()} to specify the appropriate HTTP status code.</li>
 * </ul>
 *
 * @author Juan
 * @see org.springframework.http.HttpStatus
 */
public abstract class ResponseException extends RuntimeException {

    /**
     * Constructs a new {@code ResponseException} with the specified detail message.
     *
     * @param message a descriptive message explaining the reason for the exception
     */
    public ResponseException(String message) {
        super(message);
    }

    /**
     * Returns the HTTP status code associated with this exception.
     * <p>
     * Each subclass must override this method to indicate which HTTP status
     * should be returned when this exception occurs (e.g., {@link HttpStatus#NOT_FOUND}).
     * </p>
     *
     * @return the {@link HttpStatus} corresponding to this exception
     */
    public abstract HttpStatus getHttpStatus();
}
