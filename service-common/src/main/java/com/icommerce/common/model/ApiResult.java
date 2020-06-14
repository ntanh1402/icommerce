package com.icommerce.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResult<T> {

    public static final String RESULT = "result";
    public static final String ERROR = "error";

    private T result;

    private ErrorSummary error;

    public ApiResult(T result) {
        this(result, null);
    }

    public ApiResult(ErrorSummary error) {
        this(null, error);
    }

    public ApiResult(T result, ErrorSummary error) {
        this.result = result;
        this.error = error;
    }

}
