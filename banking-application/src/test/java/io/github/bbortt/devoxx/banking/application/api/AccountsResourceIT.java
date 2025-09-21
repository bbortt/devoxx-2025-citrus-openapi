package io.github.bbortt.devoxx.banking.application.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bbortt.devoxx.banking.application.api.dto.Account;
import org.citrusframework.TestCaseRunner;
import org.citrusframework.annotations.CitrusResource;
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.automation.devoxx.api.AccountsApi;
import org.citrusframework.automation.devoxx.spring.DevoxxBeanConfiguration;
import org.citrusframework.config.CitrusSpringConfig;
import org.citrusframework.endpoint.Endpoint;
import org.citrusframework.exceptions.CitrusRuntimeException;
import org.citrusframework.http.client.HttpClient;
import org.citrusframework.http.client.HttpEndpointConfiguration;
import org.citrusframework.junit.jupiter.spring.CitrusSpringSupport;
import org.citrusframework.message.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest
@CitrusSpringSupport
@ContextConfiguration(classes = {CitrusSpringConfig.class, AccountsResourceIT.AccountsApiConfiguration.class, DevoxxBeanConfiguration.class})
class AccountsResourceIT {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private AccountsApi api;

    @Test
    @CitrusTest
    void shouldGetAccountDetails_forExistingAccountId(@CitrusResource TestCaseRunner testCaseRunner) {
        var accountId = "CH685984389182Q70Y469";

        testCaseRunner.when(
                api.sendGetAccountDetails(accountId)
        );

        testCaseRunner.then(
                api.receiveGetAccountDetails(OK)
                        .validate((message, testContext) ->
                                assertThat(getAccountFromPayload(message))
                                        .satisfies(
                                                account -> assertThat(account.getAccountId())
                                                        .isEqualTo(accountId)
                                        )
                        )
        );
    }

    private Account getAccountFromPayload(Message message) {
        try {
            return objectMapper.readValue(message.getPayload(String.class), Account.class);
        } catch (JsonProcessingException e) {
            throw new CitrusRuntimeException("Failed to extract Account from payload!", e);
        }
    }

    @Test
    @CitrusTest
    void shouldGetAccountDetails_forInvalidAccountId(@CitrusResource TestCaseRunner testCaseRunner) {
        testCaseRunner.when(
                api.sendGetAccountDetails("invalid-account-id")
        );

        testCaseRunner.then(
                api.receiveGetAccountDetails(NOT_FOUND)
                        .validate((message, testContext) -> assertThat(message.getPayload(String.class)).isEmpty())
        );
    }

    @TestConfiguration
    static class AccountsApiConfiguration {

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
