package com.icommerce.productservice.service.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseWebService {

    private static final String FORWARD_SLASH = "/";
    private static final String TERMINAL_SLASH_MSG = " with a forward slash ('/')";
    private static final String BASE_URI_MUST_BE_ABSOLUTE = "uriBase must be absolute";
    private static final String BASE_URI_ENDS_WITH_SLASH = "uriBase must end " + TERMINAL_SLASH_MSG;
    private static final String PATH_STARTS_WITH_SLASH = "path components must not start " + TERMINAL_SLASH_MSG;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected RestTemplate restTemplate;

    protected <T> T doExecute(URI uri, HttpMethod method, Object body, TypeReference<T> resultClass) {
        return restTemplate.execute(uri, method, requestCallback(body), getResponseExtractor(resultClass));
    }

    protected URI buildUrl(String... pathComponents) {
        try {
            URI baseUri = new URI(getBaseUrl());
            return combinePaths(baseUri, pathComponents);
        } catch (Exception e) {
            return null;
        }
    }

    protected URI buildUrl(MultiValueMap<String, String> params, String... pathComponents) {
        try {
            URI baseUri = new URI(getBaseUrl());
            return UriComponentsBuilder.fromUri(combinePaths(baseUri, pathComponents))
                                       .queryParams(params)
                                       .build()
                                       .toUri();
        } catch (Exception e) {
            return null;
        }
    }

    protected abstract String getBaseUrl();

    protected abstract <T> ResponseExtractor<T> getResponseExtractor(TypeReference<T> resultClass);

    private static URI combinePaths(URI uriBase, String... pathComponents) {
        if (!uriBase.isAbsolute()) {
            throw new IllegalArgumentException(BASE_URI_MUST_BE_ABSOLUTE);
        }
        if (!uriBase.toString().endsWith(FORWARD_SLASH)) {
            throw new IllegalArgumentException(BASE_URI_ENDS_WITH_SLASH);
        }

        List<String> urlEncodedPathComponents = new ArrayList<>(pathComponents.length);
        for (String pathComponent : pathComponents) {
            if (pathComponent.startsWith(FORWARD_SLASH)) {
                throw new IllegalArgumentException(PATH_STARTS_WITH_SLASH);
            }

            try {
                String encodedPath = URLEncoder.encode(pathComponent, StandardCharsets.UTF_8.name());
                urlEncodedPathComponents.add(encodedPath);
            } catch (UnsupportedEncodingException ex) {
                // Will never happen because as per docs of StandardCharsets UTF-8 encoding is
                // supported on all Java platforms.
                throw new RuntimeException(ex);
            }
        }

        String path = StringUtils.join(urlEncodedPathComponents, FORWARD_SLASH);
        return uriBase.resolve(path);
    }

    protected RequestCallback requestCallback(final Object body) {
        return clientHttpRequest -> {
            if (body != null) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(clientHttpRequest.getBody(), body);
            }
        };
    }

}
