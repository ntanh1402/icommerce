package com.icommerce.profileservice.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static com.icommerce.profileservice.service.jwt.JwtServiceImpl.ISSUER;
import static com.icommerce.profileservice.service.jwt.JwtServiceImpl.USER_ID_CLAIM;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Value(value = "${jwt.secret}")
    private String secret;

    @Test
    public void buildJwtTokenReturnAValidToken() {
        long userId = 1;
        String token = jwtService.buildJwtToken(userId);

        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
        DecodedJWT jwt = verifier.verify(token);
        Assert.assertEquals(1L, jwt.getClaim(USER_ID_CLAIM).asLong().longValue());
    }

    @Test
    public void isTokenValidReturnTrueForAValidToken() {
        long userId = 1;

        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
                          .withIssuer(ISSUER)
                          .withClaim(USER_ID_CLAIM, userId)
                          .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
                          .sign(algorithm);

        boolean result = jwtService.isTokenValid(token, userId);
        Assert.assertTrue(result);
    }

    @Test
    public void isTokenValidReturnFalseForAExpiredToken() {
        long userId = 1;

        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
                          .withIssuer(ISSUER)
                          .withClaim(USER_ID_CLAIM, userId)
                          .withExpiresAt(new Date(System.currentTimeMillis() - 3600000))
                          .sign(algorithm);

        boolean result = jwtService.isTokenValid(token, userId);
        Assert.assertFalse(result);
    }

    @Test
    public void isTokenValidReturnFalseForAUnmatchedUserIdToken() {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
                          .withIssuer(ISSUER)
                          .withClaim(USER_ID_CLAIM, 2)
                          .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
                          .sign(algorithm);

        boolean result = jwtService.isTokenValid(token, 1);
        Assert.assertFalse(result);
    }

}