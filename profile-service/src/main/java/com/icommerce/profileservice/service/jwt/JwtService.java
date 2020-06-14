package com.icommerce.profileservice.service.jwt;

public interface JwtService {

    String buildJwtToken(long userId);

    boolean isTokenValid(String token, long userId);

}
