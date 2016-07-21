package br.jus.stf.core.framework.testing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.03.2016
 */
@Configuration
@EnableRetry
public class TestsConfiguration {

    @Bean
    public RemoteServiceTestsSupport remoteService() throws Exception {
        return new RemoteServiceTestsSupport();
    }
    
}
