package com.example.connectionstuck;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableR2dbcRepositories(basePackages = "com.example.connectionstuck")
@EnableTransactionManagement
@EnableR2dbcAuditing
public class R2dbcConfig extends AbstractR2dbcConfiguration {

  @Bean
  @Override
  public ConnectionFactory connectionFactory() {
    return new PostgresR2dbcConnectionPoolFactory().create();
  }

  @Bean
  ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
    return new R2dbcTransactionManager(connectionFactory);
  }
}
