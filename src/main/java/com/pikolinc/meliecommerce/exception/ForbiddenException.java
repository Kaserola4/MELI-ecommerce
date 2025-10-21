package com.pikolinc.meliecommerce.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a client attempts to access a resource or perform an action
 * that they are not authorized to access.
 * <p>
 * This exception results in an HTTP {@code 403 Forbidden} response and
 * is typically used when the user is authenticated but lacks the necessary
 * permissions to perform the requested operation.
 * </p>
 *
 * <h2>Usage example:</h2>
 * <pre>{@code
 * if (!order.getClient().getId().equals(clientId)) {
 *     throw new ForbiddenException("Order " + orderId + " does not belong to client " + clientId);
 * }
 * }</pre>
 *
 * @see HttpStatus#FORBIDDEN
 * @see ResponseException
 */
public class ForbiddenException extends ResponseException {

    /**
     * Constructs a new {@code ForbiddenException} with the specified detail message.
     *
     * @param message a descriptive message explaining why access is forbidden
     */
    public ForbiddenException(String message) {
        super(message);
    }

    /**
     * Returns the HTTP status code for this exception.
     *
     * @return {@link HttpStatus#FORBIDDEN} (403)
     */
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
