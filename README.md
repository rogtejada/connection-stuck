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

