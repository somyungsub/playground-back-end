package prototype.hexa.webtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = {"prototype.hexa"})
@ComponentScan(basePackages = {"prototype.hexa"})
public class WebTestApplication {
  public static void main(String[] args) {
    SpringApplication.run(WebTestApplication.class, args);
  }
}