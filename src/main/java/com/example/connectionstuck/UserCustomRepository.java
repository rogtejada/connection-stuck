package com.example.connectionstuck;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.List;

public interface UserCustomRepository {

  Flux<User> findAllCustom(List<BigInteger> ids);
  Mono<Long> findCount(List<BigInteger> ids);

}
