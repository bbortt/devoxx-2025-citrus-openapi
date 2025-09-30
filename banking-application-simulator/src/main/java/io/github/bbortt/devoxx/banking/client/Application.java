package io.github.bbortt.devoxx.banking.client;

import org.citrusframework.endpoint.EndpointAdapter;
import org.citrusframework.endpoint.adapter.StaticEndpointAdapter;
import org.citrusframework.http.message.HttpMessage;
import org.citrusframework.message.Message;
import org.citrusframework.simulator.http.HttpRequestAnnotationScenarioMapper;
import org.citrusframework.simulator.http.HttpRequestPathScenarioMapper;
import org.citrusframework.simulator.http.SimulatorRestAdapter;
import org.citrusframework.simulator.http.SimulatorRestConfigurationProperties;
import org.citrusframework.simulator.scenario.mapper.ScenarioMapper;
import org.citrusframework.simulator.scenario.mapper.ScenarioMappers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@SpringBootApplication
public class Application extends SimulatorRestAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public ScenarioMapper scenarioMapper() {
        return ScenarioMappers.of(
                new HttpRequestPathScenarioMapper(),
                new HttpRequestAnnotationScenarioMapper()
        );
    }

    @Override
    public List<String> urlMappings(SimulatorRestConfigurationProperties simulatorRestConfiguration) {
        // TODO: Add URL Mappings
    }

    @Override
    public EndpointAdapter fallbackEndpointAdapter() {
        return new StaticEndpointAdapter() {
            @Override
            protected Message handleMessageInternal(Message message) {
                return new HttpMessage().status(INTERNAL_SERVER_ERROR);
            }
        };
    }
}
