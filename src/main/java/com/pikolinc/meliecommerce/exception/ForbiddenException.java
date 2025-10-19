package com.pikolinc.meliecommerce.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ResponseException {
    public ForbiddenException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
