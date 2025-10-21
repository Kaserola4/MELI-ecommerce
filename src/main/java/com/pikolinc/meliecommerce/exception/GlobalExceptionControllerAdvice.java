package com.pikolinc.meliecommerce.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * Global exception handler for the application.
 * <p>
 * Intercepts exceptions thrown by any controller and converts them into standardized HTTP responses.
 * Provides handlers for both custom {@link ResponseException} types and general exceptions,
 * including validation errors.
 * </p>
 */
@ControllerAdvice
public class GlobalExceptionControllerAdvice {

    /**
     * Standardized error response structure returned to clients.
     *
     * @param status  the HTTP status code
     * @param message a human-readable error message
     */
    public record ErrorResponse(int status, String message) {
    }

    /**
     * Handles all custom {@link ResponseException} and its subclasses.
     * Converts business logic exceptions such as {@link NotFoundException}
     * into an HTTP response with the status code defined by the exception.
     *
     * @param exception the caught ResponseException
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse} with
     * the appropriate status and message
     */
    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<ErrorResponse> handleResponseException(ResponseException exception) {
        HttpStatus httpStatus = exception.getHttpStatus();
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), exception.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    /**
     * Handles any uncaught {@link Exception} that does not have a specific handler.
     * Provides a generic HTTP 500 Internal Server Error response to prevent
     * exposing internal stack traces to clients.
     *
     * @param exception the caught exception
     * @return a {@link ResponseEntity} containing a generic error message
     * with HTTP 500 status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Oops. An unexpected error occurred. Please try again later."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Handles validation errors triggered by invalid method arguments
     * annotated with {@link jakarta.validation.Valid}.
     * Aggregates all field errors into a single semicolon-separated message.
     *
     * @param ex the {@link MethodArgumentNotValidException} containing validation errors
     * @return a {@link ResponseEntity} with HTTP 400 Bad Request and a detailed message
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        String errorMessage = String.join("; ", errors);

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handles {@link ConstraintViolationException} triggered by violations
     * of bean validation constraints outside method parameters.
     * Aggregates all constraint violation messages into a single semicolon-separated string.
     *
     * @param ex the {@link ConstraintViolationException} containing constraint violations
     * @return a {@link ResponseEntity} with HTTP 400 Bad Request and a detailed message
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .reduce((msg1, msg2) -> msg1 + "; " + msg2)
                .orElse("Validation error");

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
