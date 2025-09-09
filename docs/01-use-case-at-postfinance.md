# API-First Development at PostFinance

At PostFinance, we follow an API-first development strategy.
Instead of building producers and consumers independently and hoping they align later, we start with a shared specification.
This might be an OpenAPI specification for RESTful services or an AsyncAPI specification for event-driven architectures.

By defining the API contract upfront, we gain several advantages:

* **Parallel development** \
    Both producers and consumers can start working at the same time, as they have a clear contract to implement against.
* **Clear contracts** \
    The OpenAPI specification serves as the single source of truth, reducing misunderstandings and inconsistencies between teams.
* **Testability** \
    With a defined contract in place, tests can be derived directly from the specification.
    This makes it easier to verify whether an implementation follows the agreed-upon rules.

The diagram below illustrates this setup:

![API-First Development Diagram](../sketches/api-testing-postfinance.png)

In this diagram, we see two teams, indicated by different colors: the producer team and the consumer team.
Both teams work from the same OpenAPI specification, ensuring alignment and consistency.

By relying on the OpenAPI specification as the foundation of our process, we speed up delivery, improve collaboration, and create a natural entry point for contract-driven testing.
