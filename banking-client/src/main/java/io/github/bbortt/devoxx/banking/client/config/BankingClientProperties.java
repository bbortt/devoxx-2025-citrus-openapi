package io.github.bbortt.devoxx.banking.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "io.github.bbortt.devoxx.cli")
public class BankingClientProperties {

    private String serverUrl = "http://localhost:8080";

    public String getServerUrl() {
        return serverUrl;
    }
}
