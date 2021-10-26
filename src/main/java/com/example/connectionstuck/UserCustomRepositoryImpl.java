package com.example.connectionstuck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.List;

public class UserCustomRepositoryImpl implements UserCustomRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserCustomRepositoryImpl.class);

  private final R2dbcEntityTemplate entityTemplate;
  private final UserMapper mapper;

  public UserCustomRepositoryImpl(R2dbcEntityTemplate entityTemplate, UserMapper mapper) {
    this.entityTemplate = entityTemplate;
    this.mapper = mapper;
  }

  @Override
  public Flux<User> findAllCustom(List<BigInteger> ids) {

    return this.entityTemplate.getDatabaseClient()
        .sql("select pg_sleep(0.1) sleep, u.* from users u where id in (:ids)")
        .bind("ids", ids)
        .map(mapper)
        .all()
        .doOnNext( u -> LOGGER.info("onNext {}", u.getId()))
        .doOnCancel(() -> LOGGER.info("CANCEL findAllCustom"));
  }

}
