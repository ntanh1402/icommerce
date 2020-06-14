package com.icommerce.common.exception;

import org.springframework.http.HttpStatus;

public class UnexpectedResponseException extends WebApplicationException {

    public UnexpectedResponseException() {
        super(HttpStatus.NOT_ACCEPTABLE);
    }

    public UnexpectedResponseException(String message) {
        super(HttpStatus.NOT_ACCEPTABLE, message);
    }

    public UnexpectedResponseException(String message, Throwable throwable) {
        super(HttpStatus.NOT_ACCEPTABLE, message, throwable);
    }

    public UnexpectedResponseException(Throwable throwable) {
        super(HttpStatus.NOT_ACCEPTABLE, throwable);
    }

}
