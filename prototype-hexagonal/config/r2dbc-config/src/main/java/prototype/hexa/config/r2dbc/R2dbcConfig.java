package prototype.hexa.config.r2dbc;

import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//@Configuration
//@EnableTransactionManagement
//@EnableR2dbcAuditing
//@EnableR2dbcRepositories(
//        basePackages = "prototype.hexa.**.adapter.out.r2dbc"  //TODO
////        entityOperationsRef = "r2dbcEntityTemplate"
//)
//@Slf4j
//public class R2dbcConfig {
//  @Bean
//  public ReactiveTransactionManager r2dbcTransactionManager(ConnectionFactory connectionFactory) {
//    log.info("Creating R2dbcTransactionManager");
//    return new R2dbcTransactionManager(connectionFactory);
//  }
//}

@Configuration
@EnableTransactionManagement
@EnableR2dbcAuditing
@EnableR2dbcRepositories(basePackages = "prototype.hexa.sample.adapter.out.r2dbc")
public class R2dbcConfig {
  @Bean
  public ReactiveTransactionManager r2dbcTransactionManager(ConnectionFactory connectionFactory) {
    return new R2dbcTransactionManager(connectionFactory);
  }


//  @Bean
//  public Mono<Void> initDatabase(ConnectionFactory connectionFactory) {
//    return Mono.from(connectionFactory.create())
//      .flatMapMany(connection -> {
//        try {
//          return Flux.fromIterable(
//              Files.readAllLines(Paths.get("src/main/resources/schema.sql"))
//            ).flatMap(sql -> connection.createStatement(sql).execute())
//            .doFinally(signal -> connection.close());
//        } catch (IOException e) {
//          throw new RuntimeException(e);
//        }
//      }).then();
//  }
//
//  private final ConnectionFactory connectionFactory;
//
//  @Autowired
//  public R2dbcConfig(ConnectionFactory connectionFactory) {
//    this.connectionFactory = connectionFactory;
//  }
//  @PostConstruct
//  @Transactional
//  public void initDatabase() {
//    System.out.println("Initializing Database..."); // 로그 추가
//    // 데이터베이스 초기화 로직을 여기에 작성
//    Mono<Void> init = Mono.from(connectionFactory.create())
//      .flatMapMany(connection -> {
//        // 파일에서 SQL을 읽어오고 실행
//        try {
//          return Flux.fromIterable(
////            Files.readAllLines(Paths.get("src/main/resources/schema.sql"))
//            Files.readAllLines(Paths.get("/Users/myungsubso/Desktop/DEV/IntelliJ/hexagonal-test/config/r2dbc-config/src/main/resources/schema.sql"))
//          ).flatMap(sql -> {
//            System.out.println("Executing SQL: " + sql); // 로그 출력
//            return connection.createStatement(sql).execute();
//          }).doFinally(signal -> connection.close());
//        } catch (IOException e) {
//          throw new RuntimeException(e);
//        }
//      }).then();
//
//    // 결과를 구독하여 실행
//    init.subscribe(
//      null,
//      error -> System.err.println("Error during initialization: " + error),
//      () -> System.out.println("Database initialization complete")
//    );
//  }

}