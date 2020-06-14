package com.icommerce.productservice.service.profile;

import com.icommerce.common.exception.ForbiddenException;

public interface ProfileService {

    /**
     * Validate user authentication token
     *
     * @param userId userId
     * @param token  user token
     * @throws ForbiddenException if invalid token
     */
    void validateToken(long userId, String token);

}
