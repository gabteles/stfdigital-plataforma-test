package br.jus.stf.core.framework.testing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class OauthTestConfiguration {
    @Bean
    @Primary
    public Boolean oauth2StatelessSecurityContext() {
        return Boolean.FALSE;
    }
}