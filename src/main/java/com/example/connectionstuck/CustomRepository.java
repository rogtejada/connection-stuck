package com.example.connectionstuck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class CustomRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomRepository.class);

  private final R2dbcEntityTemplate entityTemplate;

  public CustomRepository(R2dbcEntityTemplate entityTemplate) {
    this.entityTemplate = entityTemplate;
  }

  public Mono<String> findCustom(String status) {

    return this.entityTemplate.getDatabaseClient()
        .sql("select pg_sleep(0.2) sleep, '" + status + "' as status")
        .map(row -> row.get(1, String.class))
        .one()
        .doOnNext( s -> LOGGER.info("onNext {}", s))
        .doOnCancel(() -> LOGGER.info("CANCEL findAllCustom"));
  }

}
