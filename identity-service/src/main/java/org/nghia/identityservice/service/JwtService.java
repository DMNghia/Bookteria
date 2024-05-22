package org.nghia.identityservice.service;

import org.nghia.identityservice.dto.UserLoginPrinciple;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;
    @Value("${application.security.jwt.issuer}")
    private String issuer;

//    public String generateToken(UserLoginPrinciple userLoginPrinciple) {
//
//    }
}
