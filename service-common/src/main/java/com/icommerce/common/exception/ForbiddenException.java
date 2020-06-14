package com.icommerce.common.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends WebApplicationException {

    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN);
    }

    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }

    public ForbiddenException(String message, Throwable throwable) {
        super(HttpStatus.FORBIDDEN, message, throwable);
    }

    public ForbiddenException(Throwable throwable) {
        super(HttpStatus.FORBIDDEN, throwable);
    }

}
