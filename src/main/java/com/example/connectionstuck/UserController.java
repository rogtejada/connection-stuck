package com.example.connectionstuck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/sample")
public class UserController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  private final UserRepository repository;

  public UserController(UserRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public Flux<User> sample(@RequestParam("ids") List<BigInteger> ids,
                                 ServerWebExchange exchange) {

    return repository
            .findAllCustom(ids)
            .doOnSubscribe(s -> LOGGER.info("Entering: [{}] {}",
                    exchange.getRequest().getMethod(),
                    exchange.getRequest().getURI()))
            .doOnTerminate(() -> LOGGER.info("Exit: [{}] {}",
                    exchange.getRequest().getMethod(),
                    exchange.getRequest().getURI()));

  }
}
