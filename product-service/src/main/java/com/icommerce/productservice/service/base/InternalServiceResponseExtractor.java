package com.icommerce.productservice.service.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.icommerce.common.exception.UnexpectedResponseException;
import com.icommerce.common.exception.WebApplicationException;
import com.icommerce.common.helper.JsonHelper;
import com.icommerce.common.model.ApiResult;
import com.icommerce.common.model.ErrorSummary;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.client.ResponseExtractor;

import java.io.IOException;

public class InternalServiceResponseExtractor<T> implements ResponseExtractor<T> {

    private final TypeReference<T> resultClass;

    public InternalServiceResponseExtractor(TypeReference<T> resultClass) {
        this.resultClass = resultClass;
    }

    @Override
    public T extractData(@Nullable ClientHttpResponse response) throws IOException {
        try {
            if (response == null) {
                throw new UnexpectedResponseException();
            }

            int status = response.getRawStatusCode();

            JsonNode body;
            try {
                body = JsonHelper.readFromJson(response.getBody(), JsonNode.class);
            } catch (Exception e) {
                body = new ObjectNode(JsonNodeFactory.instance);
            }
            if (status >= 200 && status <= 299) {
                return JsonHelper.readFromJson(body.get(ApiResult.RESULT).toString(), resultClass);
            } else {
                ApiResult<?> apiResult = JsonHelper.readFromJson(body, ApiResult.class);
                JsonNode result = body.get(ApiResult.RESULT);

                ErrorSummary es = apiResult.getError();
                if (es == null) {
                    // If the result contains a response, convert the response into an error summary
                    es = new ErrorSummary();

                    String jsonResult = result.toString();
                    es.setMessage(jsonResult);
                }

                throw new WebApplicationException(HttpStatus.valueOf(status), es.getMessage());
            }
        } catch (RuntimeException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IOException) {
                // thrown by either response.asJson() or Json.fromJson(JsonNode, Class)
                throw new UnexpectedResponseException(cause);
            }
            throw e;
        }
    }

}
