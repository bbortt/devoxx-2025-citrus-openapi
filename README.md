# Practical API Testing with OpenAPI and Citrus Framework

> Devoxx 2025: Belgium & Marocco

## Structure

This repository contains the following Maven projects:

```shell
/banking-application           : Production-like implementation of the Banking API
/banking-application-simulator : Citrus Simulator implementation of the Banking API
/banking-client                : CLI client to query the Banking API
```

The [`banking-application`](./banking-application) module also includes Citrus tests with actions based on the Banking API.

## Banking Application

To start the **Banking Application**, run:

```shell
./mvnw -f banking-application/pom.xml spring-boot:run
```

## CLI Client

Once tge [Banking Application](#banking-application) is running, you can use the CLI client to query it.

First, build the client JAR:

```shell
# First, build the jar
./mvnw -f banking-client/pom.xml package
```

### Examples
#### Fetch account information

```shell
java -jar banking-client/target/banking-client-1.0.0-SNAPSHOT.jar fetch-account CH685984389182Q70Y469
```

#### Create a new transaction

```shell
java -jar banking-client/target/banking-client-1.0.0-SNAPSHOT.jar create-transaction CH685984389182Q70Y469 CH077012473296D5049K3 100
```

## Simulated Banking Application

The [CLI client](#cli-client) requires a running [Banking Application](#banking-application).
During development, you can instead use the **Banking Application Simulator**, powered by Citrus.

To start the simulator, run:

```shell
./mvnw -f banking-application-simulator/pom.xml spring-boot:run
```
