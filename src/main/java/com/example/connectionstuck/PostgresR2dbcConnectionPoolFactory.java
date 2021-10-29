package com.example.connectionstuck;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

public class PostgresR2dbcConnectionPoolFactory {

  public ConnectionFactory create() {
    final String username = "postgres";
    final String password = "admin";
    final String host = "localhost";
    final String port = "5432";
    final String database = "postgres";
    final String connectionTimeout = "15000";
    final String applicationName = "sample";

    // Pool
    final String initialSize = "1";
    final String maxSize = "3";
    final String idleTimeout = "330000";
    final String maxLifetime = "900000";

    final Map<String, String> options = new HashMap<>();
    options.put("lock_timeout", "10s");

    // Creates a ConnectionPool wrapping an underlying ConnectionFactory
    final ConnectionFactory connectionFactory =
        new PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration.builder()
                .host(host)
                .port(Integer.parseInt(port))
                .username(username)
                .password(password)
                .database(database)
                .connectTimeout(Duration.ofMillis(Long.parseLong(connectionTimeout)))
                .fetchSize(1000)
                .preparedStatementCacheQueries(-1)
                .schema("public")
                .tcpKeepAlive(false)
                .tcpNoDelay(true)
                .options(options)
                .applicationName(applicationName)
                .autodetectExtensions(false)
                .build());

    return new ConnectionPool(
        ConnectionPoolConfiguration.builder(connectionFactory)
            .maxIdleTime(Duration.ofMillis(Long.parseLong(idleTimeout)))
            .initialSize(Integer.parseInt(initialSize))
            .maxSize(Integer.parseInt(maxSize))
            .acquireRetry(3)
            .maxLifeTime(Duration.ofMillis(Long.parseLong(maxLifetime)))
            .validationQuery("SELECT 1")
            .build());

    /* too many clients
    return ConnectionFactories.get(ConnectionFactoryOptions.builder()
            .option(DRIVER,"postgresql")
            .option(HOST,"localhost")
            .option(PORT,5432)
            .option(USER,"postgres")
            .option(PASSWORD,"admin")
            .option(DATABASE,"postgres")
            .build());
    */

  }
}
