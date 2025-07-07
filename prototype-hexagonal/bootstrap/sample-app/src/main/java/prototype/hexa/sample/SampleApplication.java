package prototype.hexa.sample;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

// TODO
@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = {"prototype.hexa"})
@ComponentScan(basePackages = {"prototype.hexa"})
//@EnableJpaRepositories(basePackages = "prototype.hexa.**.adapter.out.persistence", entityManagerFactoryRef = "jpaEntityManagerFactory" ,transactionManagerRef = "jpaTransactionManager")
//@EnableR2dbcRepositories(basePackages = "prototype.hexa.**.adapter.out.r2dbc")
//@EnableMongoRepositories(basePackages = "prototype.hexa.**.adapter.out.mongo")
public class SampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }
}