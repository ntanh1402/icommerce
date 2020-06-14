package com.icommerce.common.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class WebApplicationException extends RuntimeException {

    protected final HttpStatus status;

    protected WebApplicationException(HttpStatus status) {
        this.status = status;
    }

    public WebApplicationException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public WebApplicationException(HttpStatus status, String message, Throwable throwable) {
        super(message, throwable);
        this.status = status;
    }

    public WebApplicationException(HttpStatus status, Throwable throwable) {
        super(throwable);
        this.status = status;
    }

}
