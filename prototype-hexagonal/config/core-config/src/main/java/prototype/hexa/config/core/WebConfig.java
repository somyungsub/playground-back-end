package prototype.hexa.config.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import prototype.hexa.config.core.properties.CoreProperties;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(CoreProperties.class)
@Slf4j
public class WebConfig {
    private final CoreProperties coreProperties;
    @PostConstruct
    public void postConstruct() {
        log.info("WebConfig properties : {} ", coreProperties.getVersion());
        log.info("WebConfig properties : {} ", coreProperties.getArch());
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
