package com.pikolinc.meliecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
class GlobalExceptionControllerAdvice {
    /**
     * Standard error response structure returned to clients.
     *
     * @param status  HTTP status code
     * @param message human-readable error message
     */
    public record ErrorResponse(int status, String message) {
    }

    /**
     * Handles all {@link ResponseException} and its subclasses.
     * <p>
     * This method catches business logic exceptions such as
     * {@link NotFoundException}
     * converting them into appropriate HTTP responses with status codes defined
     * by each exception type.
     * </p>
     *
     * @param exception the caught ResponseException
     * @return a {@link ResponseEntity} containing the error details and appropriate HTTP status
     * @see ResponseException
     */
    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<ErrorResponse> handleResponseException(ResponseException exception) {
        HttpStatus httpStatus = exception.getHttpStatus();
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), exception.getMessage());

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    /**
     * Handles all unexpected exceptions not caught by specific handlers.
     * <p>
     * This is a catch-all handler for any {@link Exception} that is not explicitly
     * handled by other {@link ExceptionHandler} methods. It prevents stack traces
     * from being exposed to clients and returns a generic error message.
     * </p>
     *
     * @param exception the caught exception
     * @return a {@link ResponseEntity} with HTTP 500 status and a generic error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "0oops. An unexpected error occurred. Please try again later."
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        String errorMessage = String.join("; ", errors);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                errorMessage
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
