package io.github.bbortt.devoxx.banking.application.api;

import org.citrusframework.TestCaseRunner;
import org.citrusframework.annotations.CitrusResource;
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.automation.devoxx.api.AccountsApi;
import org.citrusframework.automation.devoxx.spring.DevoxxBeanConfiguration;
import org.citrusframework.config.CitrusSpringConfig;
import org.citrusframework.endpoint.Endpoint;
import org.citrusframework.http.client.HttpClient;
import org.citrusframework.http.client.HttpEndpointConfiguration;
import org.citrusframework.junit.jupiter.spring.CitrusSpringSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import static org.citrusframework.openapi.AutoFillType.REQUIRED;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@CitrusSpringSupport
@SpringBootTest(webEnvironment = NONE)
@ContextConfiguration(classes = {
        CitrusSpringConfig.class,
        DevoxxBeanConfiguration.class,
        AccountsResourceIT.AccountsEndpointConfig.class
})
class AccountsResourceIT {

    @TestConfiguration
    static class AccountsEndpointConfig {

        @Bean
        public Endpoint devoxxEndpoint() {
            var config = new HttpEndpointConfiguration();
            config.setRequestUrl("http://localhost:8080");
            return new HttpClient(config);
        }
    }
}
