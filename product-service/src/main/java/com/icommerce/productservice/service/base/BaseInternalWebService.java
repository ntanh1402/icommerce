package com.icommerce.productservice.service.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;

public abstract class BaseInternalWebService extends BaseWebService {

    protected RequestCallback requestCallback(final Object body) {
        return clientHttpRequest -> {
            if (body != null) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(clientHttpRequest.getBody(), body);
            }
            clientHttpRequest.getHeaders().add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            clientHttpRequest.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        };
    }

    protected <T> ResponseExtractor<T> getResponseExtractor(TypeReference<T> resultClass) {
        return new InternalServiceResponseExtractor<>(resultClass);
    }

}
