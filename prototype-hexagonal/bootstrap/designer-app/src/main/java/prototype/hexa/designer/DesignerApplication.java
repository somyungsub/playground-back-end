package prototype.hexa.designer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;


@ConfigurationPropertiesScan
@SpringBootApplication
@ComponentScan(basePackages = {"prototype.hexa"})
public class DesignerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DesignerApplication.class, args);
    }
}
