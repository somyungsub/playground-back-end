package prototype.hexa.sample.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import prototype.hexa.sample.config.properties.SampleProperties;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
@Slf4j
@EnableConfigurationProperties(SampleProperties.class)
public class SampleConfig {
    private final SampleProperties sampleProperties;
    @PostConstruct
    public void postConstruct() {
        log.info("test sampleProperties : {} ", sampleProperties);
//        log.info("test sampleProperties : {} ", sampleProperties.getPath(0));
//        log.info("test sampleProperties : {} ", sampleProperties.getPath(1));
//        log.info("test sampleProperties : {} ", sampleProperties.getPath(-1));
    }
}
