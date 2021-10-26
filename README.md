## Project Testing Error
- Configure connection in PostgresR2dbcConnectionPoolFactory
- build and run application 
```
./mvnw clean package

java -jar target/connection-stuck-0.0.1-SNAPSHOT.jar
```
- open a new terminal and run cancel.sh
```
sh cancel.sh
```
- check connections in database
```sql
select pid, datname, usename, application_name, backend_start, query_start, query
from pg_stat_activity
where usename = 'postgres' 
order by query_start
```
- normally in less 2 minutes all connections are stuck with field `query` with value `SELECT 1`

|pid|datname|usename|application_name|backend_start|query_start|query|
|---|-------|-------|----------------|-------------|-----------|-----|
|4726|postgres|postgres|sample|2021-10-26 13:25:47.950|2021-10-26 13:26:03.959|SELECT 1|
|4727|postgres|postgres|sample|2021-10-26 13:25:51.328|2021-10-26 13:26:05.338|SELECT 1|
|4731|postgres|postgres|sample|2021-10-26 13:26:04.858|2021-10-26 13:26:10.977|SELECT 1|

- complete log with all connections stuck -> log_connectionStuck.txt
- end of log with last stuck

```
2021-10-26 13:26:10.976  INFO 2273659 --- [or-http-epoll-1] c.e.connectionstuck.SampleController     : Entering: [GET] http://localhost:8080/v1/sample?status=cancel
2021-10-26 13:26:10.976 DEBUG 2273659 --- [or-http-epoll-1] io.r2dbc.pool.ConnectionPool             : Obtaining new connection from the driver
2021-10-26 13:26:10.976 DEBUG 2273659 --- [or-http-epoll-1] io.r2dbc.postgresql.QUERY                : [cid: 0x3][pid: 4731] Executing query: SELECT 1
2021-10-26 13:26:10.977 DEBUG 2273659 --- [or-http-epoll-1] i.r.p.client.ReactorNettyClient          : [cid: 0x3][pid: 4731] Request:  [Bind{name='B_297', parameterFormats=[], parameters=[], resultFormats=[], source='S_1'}, Describe{name='B_297', type=PORTAL}, Execute{name='B_297', rows=1000}, Flush{}]
2021-10-26 13:26:10.977 DEBUG 2273659 --- [tor-tcp-epoll-3] i.r.p.client.ReactorNettyClient          : [cid: 0x3][pid: 4731] Response: BindComplete{}
2021-10-26 13:26:10.978 DEBUG 2273659 --- [tor-tcp-epoll-3] i.r.p.client.ReactorNettyClient          : [cid: 0x3][pid: 4731] Response: RowDescription{fields=[Field{column=0, dataType=23, dataTypeModifier=-1, dataTypeSize=4, format=FORMAT_TEXT, name='?column?', table=0}]}
2021-10-26 13:26:10.978 DEBUG 2273659 --- [tor-tcp-epoll-3] i.r.p.client.ReactorNettyClient          : [cid: 0x3][pid: 4731] Response: DataRow{columns=[PooledSlicedByteBuf(ridx: 0, widx: 1, cap: 1/1, unwrapped: PooledUnsafeDirectByteBuf(ridx: 51, widx: 65, cap: 112))]}
2021-10-26 13:26:10.978 DEBUG 2273659 --- [tor-tcp-epoll-3] i.r.p.client.ReactorNettyClient          : [cid: 0x3][pid: 4731] Response: CommandComplete{command=SELECT, rowId=null, rows=1}
2021-10-26 13:26:10.978 DEBUG 2273659 --- [tor-tcp-epoll-3] i.r.p.client.ReactorNettyClient          : [cid: 0x3][pid: 4731] Request:  Close{name='B_297', type=PORTAL}
2021-10-26 13:26:10.978 DEBUG 2273659 --- [tor-tcp-epoll-3] i.r.p.client.ReactorNettyClient          : [cid: 0x3][pid: 4731] Request:  Sync{}
2021-10-26 13:26:10.979 DEBUG 2273659 --- [tor-tcp-epoll-3] i.r.p.client.ReactorNettyClient          : [cid: 0x3][pid: 4731] Response: CloseComplete{}
2021-10-26 13:26:10.979 DEBUG 2273659 --- [tor-tcp-epoll-3] i.r.p.client.ReactorNettyClient          : [cid: 0x3][pid: 4731] Response: ReadyForQuery{transactionStatus=IDLE}
2021-10-26 13:26:10.979  INFO 2273659 --- [or-http-epoll-1] c.e.connectionstuck.CustomRepository     : CANCEL findAllCustom
2021-10-26 13:26:10.981  INFO 2273659 --- [or-http-epoll-2] c.e.connectionstuck.SampleController     : Entering: [GET] http://localhost:8080/v1/sample?status=complete
2021-10-26 13:26:10.981 DEBUG 2273659 --- [or-http-epoll-2] io.r2dbc.pool.ConnectionPool             : Obtaining new connection from the driver
```

- in general we dont see `i.r.postgresql.util.FluxDiscardOnCancel  : received cancel signal` after CANCEL
```
2021-10-26 13:26:10.551  INFO 2273659 --- [or-http-epoll-7] c.e.connectionstuck.CustomRepository     : CANCEL findAllCustom
2021-10-26 13:26:10.551 DEBUG 2273659 --- [or-http-epoll-7] i.r.postgresql.util.FluxDiscardOnCancel  : received cancel signal
2021-10-26 13:26:10.551 DEBUG 2273659 --- [or-http-epoll-7] io.r2dbc.pool.PooledConnection           : Releasing connection
```