package com.example.connectionstuck;

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Flux;

public class UserCustomRepositoryImpl implements
    UserCustomRepository {

  private final R2dbcEntityTemplate entityTemplate;
  private final UserMapper mapper;

  public UserCustomRepositoryImpl(R2dbcEntityTemplate entityTemplate, UserMapper mapper) {
    this.entityTemplate = entityTemplate;
    this.mapper = mapper;
  }

  @Override
  public Flux<User> findAllCustom() {

    return this.entityTemplate.getDatabaseClient().sql("select * from user")
        .map(mapper)
        .all();

  }
}
