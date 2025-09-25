package io.github.bbortt.devoxx.banking.client.config;

import io.github.bbortt.devoxx.banking.client.ApiClient;
import io.github.bbortt.devoxx.banking.client.api.AccountsApi;
import io.github.bbortt.devoxx.banking.client.api.TransactionsApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankingApplicationConfig {

    private final BankingClientProperties bankingClientProperties;

    public BankingApplicationConfig(BankingClientProperties bankingClientProperties) {
        this.bankingClientProperties = bankingClientProperties;
    }

    @Bean
    public AccountsApi accountsApi() {
        return new AccountsApi(
                new ApiClient()
                        .setBasePath(bankingClientProperties.getServerUrl())
        );
    }

    @Bean
    public TransactionsApi transactionsApi() {
        return new TransactionsApi(
                new ApiClient()
                        .setBasePath(bankingClientProperties.getServerUrl())
        );
    }
}
