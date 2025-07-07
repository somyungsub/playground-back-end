package prototype.hexa.runtime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;


@ConfigurationPropertiesScan
@SpringBootApplication
@ComponentScan(basePackages = {"prototype.hexa"})
public class RuntimeApplication {
    public static void main(String[] args) {
        SpringApplication.run(RuntimeApplication.class, args);
    }
}
