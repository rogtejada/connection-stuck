package com.example.connectionstuck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/v1/sample")
public class SampleController {

  private final SampleRepository repository;

  public SampleController(SampleRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public Flux<Lala> sample() {
    return repository.sampleSelect();
  }
}
