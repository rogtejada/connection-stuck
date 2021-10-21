package com.example.connectionstuck;


import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class SampleRepository {

  private final R2dbcEntityTemplate entityTemplate;

  public SampleRepository(R2dbcEntityTemplate entityTemplate) {
    this.entityTemplate = entityTemplate;
  }

  public Flux<Lala> sampleSelect()  {
    return entityTemplate
        .select(Lala.class)
        .all();
  }
}
