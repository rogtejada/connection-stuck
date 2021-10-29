package com.example.connectionstuck;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
public class NativeRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(NativeRepository.class);
  private ConnectionFactory factory;

  public NativeRepository() {
    this.factory = new PostgresR2dbcConnectionPoolFactory().create();
  }

  public Mono<String> findCustom(String status) {

    return Flux.usingWhen(factory.create(),
                   c -> c.createStatement("select pg_sleep(0.2) sleep, '" + status + "' as status").execute(),
                   Connection::close)
        .flatMap(it -> it.map((r, m) -> r.get(1, String.class)))
        .next()
        .doOnNext( s -> LOGGER.info("onNext native {}", s))
        .doOnCancel(() -> LOGGER.info("CANCEL findAllCustom native"));

  }

}
