package prototype.hexa.config.rdb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;


@Configuration
@RequiredArgsConstructor
@Slf4j
//@EnableConfigurationProperties(RdbConfigProperties.class)
@EnableConfigurationProperties
@EnableTransactionManagement
@EnableJdbcRepositories(
  basePackages = {
    "prototype.hexa.**.adapter.out.persistence",
    "prototype.hexa.**.repository"
  },
  transactionManagerRef = "jdbcTransactionManager"
)
@EnableJpaRepositories(
  basePackages = {
    "prototype.hexa.**.adapter.out.persistence",
    "prototype.hexa.**.repository"
  }, // TODO
  entityManagerFactoryRef = "jpaEntityManagerFactory",
  transactionManagerRef = "jpaTransactionManager"
)
// TODO
public class RdbConfig {
  //  private final RdbConfigProperties rdbConfigProperties;
  @PostConstruct
  public void postConstruct() throws SQLException {
//    System.out.println("Datasource URL: " + dataSource().getConnection().getMetaData().getURL());

//    log.info("DataConfigProperties key : {}", rdbConfigProperties.getKey());
//    log.info("DataConfigProperties value : {}", rdbConfigProperties.getValue());

//    HikariDataSource ds = (HikariDataSource) dataSource();
//    System.out.println("Datasource URL: " + ds.getJdbcUrl());
//    System.out.println("Datasource Username: " + ds.getUsername());
  }

//  @Bean
//  @Primary
//  @ConfigurationProperties(prefix = "spring.datasource")
//  public DataSource dataSource() {
//    return DataSourceBuilder.create().build();
//  }

//  @Bean
//  @Primary
//  @ConfigurationProperties("spring.datasource")
//  public DataSource dataSource() {
//    HikariDataSource dataSource = DataSourceBuilder.create()
//      .type(HikariDataSource.class)
//      .build();
//    log.info("Datasource URL: " + dataSource.getJdbcUrl());
//    log.info("Datasource Username: " + dataSource.getUsername());
//    return dataSource;
//  }

//  @Bean
//  @Primary
//  @ConfigurationProperties(prefix = "spring.datasource")
//  public DataSource dataSource() {
//    return DataSourceBuilder.create().build();
//  }

//  @Bean
//  @Primary
//  @ConfigurationProperties(prefix = "spring.datasource")
//  public DataSource dataSource() {
//    DataSource build = DataSourceBuilder.create().type(HikariDataSource.class).build();
//    return build;
//  }

//  @Bean
//  @Primary
//  public DataSource dataSource() {
//    HikariDataSource dataSource = new HikariDataSource();
//    dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/test");
//    dataSource.setUsername("root");
//    dataSource.setPassword("password");
//    dataSource.setDriverClassName("org.postgresql.Driver");
////    Properties properties = new Properties();
////    properties.put("rewriteBatchedInserts", true);
////    dataSource.setDataSourceProperties(properties);
//    return dataSource;
//  }

//  @Bean
//  @Primary
//  @ConfigurationProperties("spring.datasource.hikari")
//  public DataSource dataSource() {
//    return DataSourceBuilder.create()
//      .type(HikariDataSource.class)
//      .build();
//  }


//  @Bean
//  @Primary
//  public DataSource dataSource() {
//    HikariDataSource dataSource = new HikariDataSource();
//    dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/test");
//    dataSource.setUsername("root");
//    dataSource.setPassword("password");
//    dataSource.setDriverClassName("org.postgresql.Driver");
//    return dataSource;
//  }

  @Bean
  @Primary
  public DataSource dataSource() {
    return DataSourceBuilder.create()
      .url("jdbc:postgresql://localhost:6432/test")
      .username("root")
      .password("password")
      .build();
  }

  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean jpaEntityManagerFactory(DataSource dataSource) {
    List<String> entityPaths = List.of(
      "prototype.hexa.**.adapter.out.persistence",
      "prototype.hexa.**.repository"
    );
    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setDataSource(dataSource);
    factory.setPackagesToScan(entityPaths.toArray(new String[0])); // 엔티티 경로
    factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    factory.setJpaProperties(hibernateProperties());
    return factory;
  }

//  @Bean
//  @Primary
//  public LocalContainerEntityManagerFactoryBean jpaEntityManagerFactory(EntityManagerFactoryBuilder builder) {
//    return builder
//      .dataSource(dataSource())
//      .packages("prototype.hexa.**.adapter.out.persistence")
//      .persistenceUnit("jpa")
//      .build();
//  }

  @Bean
  public PlatformTransactionManager jdbcTransactionManager() {
    return new DataSourceTransactionManager(dataSource());
  }
  @Bean
  @Primary
  public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory jpaEntityManagerFactory) {
    return new JpaTransactionManager(jpaEntityManagerFactory);
  }

//  @Bean
//  public PlatformTransactionManager jdbcTransactionManager() {
//    return new DataSourceTransactionManager(dataSource());
//  }

  // Hibernate 속성 추가
  @Bean
  public Properties hibernateProperties() {
    Properties properties = new Properties();
    properties.put("hibernate.hbm2ddl.auto", "update"); // 테이블 자동 생성
//    properties.put("hibernate.ddl-auto", "create");   // 테이블 자동 생성
    properties.put("hibernate.default_schema", "sample");
    properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
    properties.put("hibernate.show_sql", "true");
    properties.put("hibernate.format_sql", "true");
    return properties;
  }


//  @Bean
//  public LocalContainerEntityManagerFactoryBean jpaEntityManagerFactory(EntityManagerFactoryBuilder builder) {
//    return builder
//            .dataSource(dataSource())
//            .packages("prototype.hexa")
//            .build();
//  }
//
//  @Bean
//  public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
//    return new JpaTransactionManager(entityManagerFactory);
//  }

}
