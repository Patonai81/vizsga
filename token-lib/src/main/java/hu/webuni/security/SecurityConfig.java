package hu.webuni.security;

import com.auth0.jwt.algorithms.Algorithm;
import hu.webuni.security.service.KeyManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    public final KeyManagerService keyManagerService;

    @Bean("signer")
    Algorithm getSignerAlg(){
        try {
            return keyManagerService.getSigner();
        } catch (Exception e) {
            log.error("Unable to init Signer");
        }
        return null;
    }

    @Bean("verifier")
    Algorithm getVerifierAlg(){
        try {
            return keyManagerService.getVerifier();
        } catch (Exception e) {
            log.error("Unable to init Verifier",e);
        }
        return null;
    }

}
