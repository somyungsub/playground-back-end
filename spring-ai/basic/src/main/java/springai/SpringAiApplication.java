package springai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springai.config.property.AppProperty;

@SpringBootApplication
@EnableConfigurationProperties({
        AppProperty.class
})
public class SpringAiApplication {
  public static void main(String[] args) {
    SpringApplication.run(SpringAiApplication.class, args);
  }
}