# Testing with the Citrus Framework at PostFinance

At PostFinance, we rely heavily on the **Citrus Framework** and the **Citrus Simulator** to enable contract-based testing and system simulations.
To fit our needs, we maintain **internal forks** of the open-source repositories.

* Our internal versions contain **PostFinance-specific extensions**, such as authentication mechanisms or shared utilities that don’t belong in the public codebase.
* These releases are published to our internal Artifactory, making them easily accessible for all our teams.
* Whenever we develop features that are broadly useful, we contribute them back to the open-source project, helping the wider community.

The following diagram illustrates how our setup relates to the open-source Citrus ecosystem:

![Testing with the Citrus Framework at PostFinance](../sketches/citrus-framework-landscape.png)
