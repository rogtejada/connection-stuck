package com.example.connectionstuck;

import io.r2dbc.spi.Row;
import java.math.BigInteger;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Function<Row, User> {

  @Override
  public User apply(Row row) {
    final var user = new User();
    user.setId(row.get("id", BigInteger.class));
    user.setName(row.get("name", String.class));

    return user;
  }

}
