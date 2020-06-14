package com.icommerce.productservice.service.profile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.icommerce.common.exception.ForbiddenException;
import com.icommerce.productservice.service.base.BaseInternalWebService;
import com.icommerce.productservice.service.profile.dto.VerifyTokenRequest;
import com.icommerce.productservice.service.profile.dto.VerifyTokenResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service("ProfileService")
public class ProfileServiceImpl extends BaseInternalWebService implements ProfileService {

    final static String USERS = "users";

    final static String VERIFY_TOKEN = "verifyToken";

    @Value("${profile.service.url}")
    private String url;

    @Override
    protected String getBaseUrl() {
        return url;
    }

    @Override
    public void validateToken(long userId, String token) {
        VerifyTokenResult result = doExecute(buildUrl(USERS, VERIFY_TOKEN),
                                             HttpMethod.POST,
                                             new VerifyTokenRequest(userId, token),
                                             new TypeReference<VerifyTokenResult>() {
                                             });

        if (result == null || !result.isValid()) {
            throw new ForbiddenException("Dont have permission to do the action!");
        }
    }

}
