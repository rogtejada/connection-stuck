package com.example.connectionstuck;

import reactor.core.publisher.Flux;

public interface UserCustomRepository {

  Flux<User> findAllCustom();

}
