package com.example.springsecurityjwt.global.jwt;

import java.util.Date;
import com.example.springsecurityjwt.user.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtProvider {
    private static final String SUBJECT = "jwt";
    private static final int ACCESS_TOKEN_EXP = Integer.parseInt(System.getenv("JWT_ACCESS_TOKEN_EXP"));
    private static final int REFRESH_TOKEN_EXP = Integer.parseInt(System.getenv("JWT_REFRESH_TOKEN_EXP"));
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String ACCESS_TOKEN_HEADER = "Authorization_Access";
    public static final String REFRESH_TOKEN_HEADER = "Authorization_Refresh";
    private static final String ACCESS_SECRET = System.getenv("JWT_ACCESS_HS512_SECRET");
    private static final String REFRESH_SECRET = System.getenv("JWT_REFRESH_HS512_SECRET");

    public static String createAccessToken(User user) {
        String jwt = JWT.create()
                .withSubject(SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXP))
                .withClaim("id", user.getId())
                .withClaim("role", user.getRole().getKey())
                .sign(Algorithm.HMAC512(ACCESS_SECRET));
        return TOKEN_PREFIX + jwt;
    }

    public static String createRefreshToken(User user) {
        String jwt = JWT.create()
                .withSubject(SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXP))
                .withClaim("id", user.getId())
                .withClaim("role", user.getRole().getKey())
                .sign(Algorithm.HMAC512(REFRESH_SECRET));
        return TOKEN_PREFIX + jwt;
    }

    public static DecodedJWT verifyAccessToken(String jwt) throws SignatureVerificationException, TokenExpiredException {
        return JWT.require(Algorithm.HMAC512(ACCESS_SECRET))
                .build().verify(jwt);
    }

    public static DecodedJWT verifyRefreshToken(String jwt) throws SignatureVerificationException, TokenExpiredException {
        return JWT.require(Algorithm.HMAC512(REFRESH_SECRET))
                .build().verify(jwt);
    }
}
