package com.icommerce.profileservice.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.icommerce.profileservice.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("JwtService")
public class JwtServiceImpl implements JwtService {

    static final String ISSUER = "icommerce";
    static final String USER_ID_CLAIM = "userId";
    static final long VALID_MILLIS = 2592000000L; //30 days

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final Algorithm algorithm;

    @Autowired
    public JwtServiceImpl(@Value(value = "${jwt.secret}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    @Override
    public String buildJwtToken(long userId) {
        Date expireDate = new Date(System.currentTimeMillis() + VALID_MILLIS);
        return JWT.create()
                  .withIssuer(ISSUER)
                  .withClaim(USER_ID_CLAIM, userId)
                  .withExpiresAt(expireDate)
                  .sign(algorithm);
    }

    @Override
    public boolean isTokenValid(String token, long userId) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
            DecodedJWT jwt = verifier.verify(token);

            return jwt.getClaim(USER_ID_CLAIM) != null &&
                   Long.valueOf(userId).equals(jwt.getClaim(USER_ID_CLAIM).asLong());
        } catch (JWTVerificationException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

}
