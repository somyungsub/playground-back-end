package prototype.hexa.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import prototype.hexa.swagger.properties.SwaggerProperties;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfig {
  private final SwaggerProperties properties;

  // http://localhost:포트/swagger-ui/index.html
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
            .info(new Info()
                    .title(properties.getTitle())
                    .version(properties.getVersion())
                    .description(properties.getDescription())
            );
//            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
//            .components(new io.swagger.v3.oas.models.Components()
//                    .addSecuritySchemes("bearerAuth", new SecurityScheme()
//                            .name("bearerAuth")
//                            .type(SecurityScheme.Type.HTTP)
//                            .scheme("bearer")
//                            .bearerFormat("JWT")));
  }

}
