package com.icommerce.common.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends WebApplicationException {

    public InternalServerErrorException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public InternalServerErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public InternalServerErrorException(String message, Throwable throwable) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message, throwable);
    }

    public InternalServerErrorException(Throwable throwable) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, throwable);
    }

}
