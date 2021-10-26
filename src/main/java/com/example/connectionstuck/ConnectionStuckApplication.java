package com.example.connectionstuck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConnectionStuckApplication {

  public static void main(String[] args) {

    System.setProperty("logging.level.io.r2dbc.postgresql", "DEBUG");
    System.setProperty("logging.level", "TRACE");
    System.setProperty("logging.level.io.r2dbc.pool", "TRACE");
    SpringApplication.run(ConnectionStuckApplication.class, args);
  }

}
