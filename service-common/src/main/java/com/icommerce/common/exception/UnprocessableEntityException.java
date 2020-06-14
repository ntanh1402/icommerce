package com.icommerce.common.exception;

import org.springframework.http.HttpStatus;

public class UnprocessableEntityException extends WebApplicationException {

    public UnprocessableEntityException() {
        super(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public UnprocessableEntityException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }

    public UnprocessableEntityException(String message, Throwable throwable) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message, throwable);
    }

    public UnprocessableEntityException(Throwable throwable) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, throwable);
    }

}
