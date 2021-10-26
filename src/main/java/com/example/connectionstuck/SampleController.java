package com.example.connectionstuck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/v1/sample")
public class SampleController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);

  private final CustomRepository repository;

  public SampleController(CustomRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public Mono<String> sample(@RequestParam("status") String status,
                             ServerWebExchange exchange) {

    return repository
            .findCustom(status)
            .doOnSubscribe(s -> LOGGER.info("Entering: [{}] {}",
                    exchange.getRequest().getMethod(),
                    exchange.getRequest().getURI()))
            .doOnTerminate(() -> LOGGER.info("Exit: [{}] {}",
                    exchange.getRequest().getMethod(),
                    exchange.getRequest().getURI()));

  }
}
