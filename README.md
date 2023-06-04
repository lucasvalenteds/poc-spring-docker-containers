# POC: Spring Docker Containers

It demonstrates how to configure Spring to provision infrastructure dependencies using Docker containers.

We want to develop a REST API that depends on database and cache components and to have those dependencies provisioned
by the framework using Docker containers during application startup when running locally and when running automated
tests as well. For the first use-case we're going to use the Docker Compose support added in Spring Boot 3.1 and for
the second one we're going to use the ServiceConnections abstractions provided in the same release.

When running the application locally the framework is going to use the Docker Compose file created inside the source
folder and manage all services declared there, meaning starting the containers before creating the application context
and destroying them on application shutdown. When running the automated tests we're going to import test configurations
classes that provides Testcontainers's container instances as beans, similar to built-in test slices, according to our
needs. Database migrations using Flyway is supported without any configuration.

The database chosen is Postgres and cache database is Redis. The source code is validated using unit tests and
integration tests and no manual configuration should be done manually to run the application locally or the test suite
for better developer experience.

## How to run

| Description     | Command             |
|:----------------|:--------------------|
| Run tests       | `./gradlew test`    |
| Run application | `./gradlew bootRun` |

## Preview

Application startup log:

```text
2023-06-04T14:06:56.462-03:00  INFO 38285 --- [           main] com.example.Application                  : Starting Application using Java 20.0.1 with PID 38285 (/home/user/poc-spring-docker-containers/build/classes/java/main started by user in /home/user/poc-spring-docker-containers)
2023-06-04T14:06:56.464-03:00  INFO 38285 --- [           main] com.example.Application                  : The following 1 profile is active: "local"
2023-06-04T14:06:56.495-03:00  INFO 38285 --- [           main] .s.b.d.c.l.DockerComposeLifecycleManager : Using Docker Compose file '/home/user/poc-spring-docker-containers/src/main/docker/docker-compose.yaml'
2023-06-04T14:06:56.712-03:00  INFO 38285 --- [utReader-stderr] o.s.boot.docker.compose.core.DockerCli   :  Container docker-redis-1  Creating
2023-06-04T14:06:56.712-03:00  INFO 38285 --- [utReader-stderr] o.s.boot.docker.compose.core.DockerCli   :  Container docker-postgres-1  Creating
2023-06-04T14:06:56.855-03:00  INFO 38285 --- [utReader-stderr] o.s.boot.docker.compose.core.DockerCli   :  Container docker-postgres-1  Created
2023-06-04T14:06:56.885-03:00  INFO 38285 --- [utReader-stderr] o.s.boot.docker.compose.core.DockerCli   :  Container docker-redis-1  Created
2023-06-04T14:06:56.886-03:00  INFO 38285 --- [utReader-stderr] o.s.boot.docker.compose.core.DockerCli   :  Container docker-postgres-1  Starting
2023-06-04T14:06:56.886-03:00  INFO 38285 --- [utReader-stderr] o.s.boot.docker.compose.core.DockerCli   :  Container docker-redis-1  Starting
2023-06-04T14:06:57.306-03:00  INFO 38285 --- [utReader-stderr] o.s.boot.docker.compose.core.DockerCli   :  Container docker-redis-1  Started
2023-06-04T14:06:57.343-03:00  INFO 38285 --- [utReader-stderr] o.s.boot.docker.compose.core.DockerCli   :  Container docker-postgres-1  Started
2023-06-04T14:06:57.343-03:00  INFO 38285 --- [utReader-stderr] o.s.boot.docker.compose.core.DockerCli   :  Container docker-redis-1  Waiting
2023-06-04T14:06:57.343-03:00  INFO 38285 --- [utReader-stderr] o.s.boot.docker.compose.core.DockerCli   :  Container docker-postgres-1  Waiting
2023-06-04T14:06:57.844-03:00  INFO 38285 --- [utReader-stderr] o.s.boot.docker.compose.core.DockerCli   :  Container docker-redis-1  Healthy
2023-06-04T14:06:57.845-03:00  INFO 38285 --- [utReader-stderr] o.s.boot.docker.compose.core.DockerCli   :  Container docker-postgres-1  Healthy
2023-06-04T14:06:59.792-03:00  INFO 38285 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Multiple Spring Data modules found, entering strict repository configuration mode
2023-06-04T14:06:59.792-03:00  INFO 38285 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2023-06-04T14:06:59.802-03:00  INFO 38285 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 6 ms. Found 0 JPA repository interfaces.
2023-06-04T14:06:59.813-03:00  INFO 38285 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Multiple Spring Data modules found, entering strict repository configuration mode
2023-06-04T14:06:59.814-03:00  INFO 38285 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data Redis repositories in DEFAULT mode.
2023-06-04T14:06:59.819-03:00  INFO 38285 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 1 ms. Found 0 Redis repository interfaces.
2023-06-04T14:07:00.082-03:00  INFO 38285 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2023-06-04T14:07:00.087-03:00  INFO 38285 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2023-06-04T14:07:00.087-03:00  INFO 38285 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.8]
2023-06-04T14:07:00.125-03:00  INFO 38285 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2023-06-04T14:07:00.125-03:00  INFO 38285 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 601 ms
2023-06-04T14:07:00.199-03:00  INFO 38285 --- [           main] o.f.c.internal.license.VersionPrinter    : Flyway Community Edition 9.16.3 by Redgate
2023-06-04T14:07:00.199-03:00  INFO 38285 --- [           main] o.f.c.internal.license.VersionPrinter    : See release notes here: https://rd.gt/416ObMi
2023-06-04T14:07:00.199-03:00  INFO 38285 --- [           main] o.f.c.internal.license.VersionPrinter    : 
2023-06-04T14:07:00.278-03:00  INFO 38285 --- [           main] o.f.c.i.database.base.BaseDatabaseType   : Database: jdbc:postgresql://127.0.0.1:32848/example (PostgreSQL 15.1)
2023-06-04T14:07:00.298-03:00  INFO 38285 --- [           main] o.f.c.i.s.JdbcTableSchemaHistory         : Schema history table "public"."flyway_schema_history" does not exist yet
2023-06-04T14:07:00.299-03:00  INFO 38285 --- [           main] o.f.core.internal.command.DbValidate     : Successfully validated 1 migration (execution time 00:00.007s)
2023-06-04T14:07:00.305-03:00  INFO 38285 --- [           main] o.f.c.i.s.JdbcTableSchemaHistory         : Creating Schema History table "public"."flyway_schema_history" ...
2023-06-04T14:07:00.372-03:00  INFO 38285 --- [           main] o.f.core.internal.command.DbMigrate      : Current version of schema "public": << Empty Schema >>
2023-06-04T14:07:00.374-03:00  INFO 38285 --- [           main] o.f.core.internal.command.DbMigrate      : Migrating schema "public" to version "1 - example migration"
2023-06-04T14:07:00.382-03:00  INFO 38285 --- [           main] o.f.core.internal.command.DbMigrate      : Successfully applied 1 migration to schema "public", now at version v1 (execution time 00:00.012s)
2023-06-04T14:07:00.406-03:00  INFO 38285 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2023-06-04T14:07:00.425-03:00  INFO 38285 --- [           main] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection org.postgresql.jdbc.PgConnection@37d3e140
2023-06-04T14:07:00.426-03:00  INFO 38285 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2023-06-04T14:07:00.447-03:00  INFO 38285 --- [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
2023-06-04T14:07:00.468-03:00  INFO 38285 --- [           main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 6.2.2.Final
2023-06-04T14:07:00.469-03:00  INFO 38285 --- [           main] org.hibernate.cfg.Environment            : HHH000406: Using bytecode reflection optimizer
2023-06-04T14:07:00.528-03:00  INFO 38285 --- [           main] o.h.b.i.BytecodeProviderInitiator        : HHH000021: Bytecode provider name : bytebuddy
2023-06-04T14:07:00.597-03:00  INFO 38285 --- [           main] o.s.o.j.p.SpringPersistenceUnitInfo      : No LoadTimeWeaver setup: ignoring JPA class transformer
2023-06-04T14:07:00.624-03:00  INFO 38285 --- [           main] org.hibernate.orm.dialect                : HHH035001: Using dialect: org.hibernate.dialect.PostgreSQLDialect, version: org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator$DialectResolutionInfoImpl@6cc8c13c
2023-06-04T14:07:00.662-03:00  INFO 38285 --- [           main] o.h.b.i.BytecodeProviderInitiator        : HHH000021: Bytecode provider name : bytebuddy
2023-06-04T14:07:00.756-03:00  INFO 38285 --- [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
2023-06-04T14:07:00.759-03:00  INFO 38285 --- [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2023-06-04T14:07:00.911-03:00  WARN 38285 --- [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
2023-06-04T14:07:01.099-03:00  INFO 38285 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2023-06-04T14:07:01.105-03:00  INFO 38285 --- [           main] com.example.Application                  : Started Application in 4.825 seconds (process running for 5.073)
```

Manual testing output [1/2]:

```shell
curl --silent http://localhost:8080/time/database
```

```json
{
  "timestamp": "2023-06-04T17:14:09.713834Z"
}
```

Manual testing output [2/2]:

```shell
curl --silent http://localhost:8080/time/cache
```

```json
{
  "timestamp": "2023-06-04T17:15:27.090Z"
}
```
