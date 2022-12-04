package hu.webuni.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Autowired
    @Qualifier("signer")
    private Algorithm signerAlg;

    @Autowired
    @Qualifier("verifier")
    private Algorithm verifierAlg;


    public static final String ISSUER = "LoginApp";
    public static final String AUTH = "auth";

    public String createJwTToken(UserDetails principal) {

      return  JWT.create().withSubject(principal.getUsername())
                .withArrayClaim(AUTH,principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                .withExpiresAt(new Date(System.currentTimeMillis()+ TimeUnit.MINUTES.toMillis(2)))
                .withIssuer(ISSUER)
                .sign(signerAlg);
    }


    public UserDetails parseJWT(String jwtToken) {
        DecodedJWT decodedJWT = JWT.require(verifierAlg).withIssuer(ISSUER).build().verify(jwtToken);
        return new User(decodedJWT.getSubject(),"",decodedJWT.getClaim(AUTH).asList(String.class).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }
}
