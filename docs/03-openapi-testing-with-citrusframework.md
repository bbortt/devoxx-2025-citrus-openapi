# OpenAPI-Based Improvements

A recent focus area has been OpenAPI-based testing and simulation.
These improvements, which we also contributed to upstream, include:

* **Citrus Actions Generated from OpenAPI Specifications**

    Each resource in the specification can be turned into a Citrus action, enabling us to build test cases quickly.

* **Intelligent DSL Support**

    When querying APIs, the DSL knows which parameters are required and validates requests accordingly.
    This makes it easier to write correct test automation.

* **Schema Validation**

    Both outgoing requests and incoming responses are validated against the OpenAPI specification, ensuring strict contract compliance.

The following diagram shows how the **OpenAPI Generator** creates these Citrus actions directly from the specification:

![Citrus OpenAPI Generator](../sketches/citrus-openapi-generator.png)

* **Randomized Simulations**

    The Citrus Simulator can now generate randomized responses based on the specification.
    This is especially valuable in early development stages, where frontend and backend teams at PostFinance often implement independently but still need meaningful test data.

Here is how this looks in the **Citrus Simulator**:

![Citrus Simulator](../sketches/citrus-simulator.png)

By combining API-first development with these Citrus capabilities, we can test and simulate reliably long before full integration takes place.
