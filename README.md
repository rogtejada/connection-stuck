## Project Testing Error
- Configure connection in PostgresR2dbcConnectionPoolFactory
- Execute script in new database
```sql  
  
CREATE TABLE public.users  
(  
 id bigint NOT NULL, name character varying(100) COLLATE pg_catalog."default" NOT NULL, CONSTRAINT user_pkey PRIMARY KEY (id));  
  
insert into users values(1, 'Test 1');  
insert into users values(2, 'Test 2');  
  
```
- start Application with breakpoint in `DefaultFetchSpec` line `49`
```java
return ((Flux)this.resultFunction.apply(connection)).flatMap((result) -> {
```
- in some cases it is necessary to put the break point on line `48`

- call endpoint
```
curl --location --request GET 'http://localhost:8080/v1/sample'
```
- wait for execution to stop at break point and cancel request
- release application
- check that the connection will be locked
```sql
select pid, datname, usename, application_name, backend_start, query_start, query
from pg_stat_activity
where usename = 'postgres' 
order by query_start
```
- field `query` with value `SELECT 1`
- repeat the endpoint call following the steps until all connections are locked
