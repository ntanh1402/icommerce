package com.icommerce.profileservice.helper;

import com.icommerce.common.exception.UnprocessableEntityException;

public class FacebookLoginHelper {

    /**
     * Get facebook id by calling facebook apis
     *
     * @param token facebook token
     * @return facebookId
     * @throws UnprocessableEntityException if can not get facebook Id
     */
    public static String fetchFacebookId(String token) {
        //TODO call facebook api to get facebook id.

        //TODO here is just a mock exam
        if ("fail".equalsIgnoreCase(token)) {
            throw new UnprocessableEntityException("Cannot get facebookId");
        }

        return token;
    }

}
