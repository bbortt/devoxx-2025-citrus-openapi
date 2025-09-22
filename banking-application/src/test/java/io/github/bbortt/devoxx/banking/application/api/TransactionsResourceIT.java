package io.github.bbortt.devoxx.banking.application.api;

import org.citrusframework.TestCaseRunner;
import org.citrusframework.annotations.CitrusResource;
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.automation.devoxx.api.TransactionsApi;
import org.citrusframework.automation.devoxx.spring.DevoxxBeanConfiguration;
import org.citrusframework.config.CitrusSpringConfig;
import org.citrusframework.endpoint.Endpoint;
import org.citrusframework.http.client.HttpClient;
import org.citrusframework.http.client.HttpEndpointConfiguration;
import org.citrusframework.junit.jupiter.spring.CitrusSpringSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.citrusframework.openapi.AutoFillType.REQUIRED;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@CitrusSpringSupport
@SpringBootTest(webEnvironment = NONE)
@ContextConfiguration(classes = {CitrusSpringConfig.class, TransactionsResourceIT.TransactionsApiConfiguration.class, DevoxxBeanConfiguration.class})
class TransactionsResourceIT {

    @Autowired
    private TransactionsApi transactionsApi;

    @Test
    @CitrusTest
    void shouldRejectRequestWithoutBody(@CitrusResource TestCaseRunner testCaseRunner) {
        testCaseRunner.when(
                transactionsApi.sendCreateTransaction(null)
                        .autoFill(REQUIRED)
        );

        testCaseRunner.then(
                transactionsApi.receiveCreateTransaction(BAD_REQUEST)
                        .validate((message, testContext) -> assertThat(message.getPayload(String.class)).isEmpty())
        );
    }

    @Test
    @CitrusTest
    void shouldRejectRequestWithInvalidAccountsNumbers(@CitrusResource TestCaseRunner testCaseRunner) {
        testCaseRunner.when(
                transactionsApi.sendCreateTransaction(null)
                        .autoFill(REQUIRED)
        );

        testCaseRunner.then(
                transactionsApi.receiveCreateTransaction(FORBIDDEN)
                        .validate((message, testContext) -> assertThat(message.getPayload(String.class)).isEmpty())
        );
    }

    @TestConfiguration
    static class TransactionsApiConfiguration {

        @Bean
        public Endpoint devoxxEndpoint(@Value("${server.port}") String serverPort) {
            var endpointConfiguration = new HttpEndpointConfiguration();
            endpointConfiguration.setRequestUrl(
                    format("http://localhost:%s", serverPort)
            );
            return new HttpClient(endpointConfiguration);
        }
    }
}
