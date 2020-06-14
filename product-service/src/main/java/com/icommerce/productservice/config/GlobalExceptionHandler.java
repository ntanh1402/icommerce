package com.icommerce.productservice.config;

import com.icommerce.common.exception.WebApplicationException;
import com.icommerce.common.model.ApiResult;
import com.icommerce.common.model.ErrorSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(WebApplicationException.class)
    public ResponseEntity<Object> handleWebApplicationException(HttpServletRequest request,
                                                                WebApplicationException ex) {
        logger.error("Exception occurred:: URL=" + request.getRequestURL(), ex);
        return new ResponseEntity<>(new ApiResult<>(new ErrorSummary(String.valueOf(ex.getStatus().value()),
                                                                     ex.getMessage())),
                                    new HttpHeaders(),
                                    ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(HttpServletRequest request, Exception ex) {
        logger.error("Exception occurred:: URL=" + request.getRequestURL(), ex);
        return new ResponseEntity<>(new ApiResult<>(new ErrorSummary("500",
                                                                     "Something wrong happened. Please contact admin!")),
                                    new HttpHeaders(),
                                    HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
