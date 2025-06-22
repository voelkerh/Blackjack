# Architecture

This is a short description of our architecture choice and its implications for our blackjack service.

## Choice

We chose hexagonal architecture for our project.
This type of architecture is also known as ports and adapters architecture.
The hexagon metaphorically refers to a central core with the domain logic surrounded by an application layer.
The application layer comprises several use cases, which should be modeled as concrete components.
Use cases are named after an activity type and act as orchestrators.
On the margin of the application layer there are ports.
These are abstractions for the applications interaction with the surrounding system.
We have inbound / driving ports on the left and outbound / driven ports on the right.
Adapters form an own layer around the application and are concrete implementations of the port abstractions.
Dependencies always flow from the outside to the inside, never the other way around. This means that the directions of
dependencies for inbound and outbound ports is inverted.

Check https://www.happycoders.eu/de/software-craftsmanship/hexagonale-architektur/

## Transfer to Blackjack Use Case

A client sends HTTP-requests via RESTful API to the blackjack micro-service.
A concrete REST-controller accepts these requests. In hexagonal vocabulary, the controller is an "adapter".
This adapter knows inbound port interfaces, which are an abstract description of the "use cases".
Thus, the adapter has use cases as dependencies and receives their implementations during run time.
The concrete use cases are named "Services" (such as CalculateChancesService, GetRulesService, PlayService, GetStatsService)
or "Handlers" and implement the inbound port interfaces. They should be implemented with the strategy pattern.

The Services/Handlers form the application layer. The Services have dependencies on elements of a utility layer.
This comprises factory classes for elements of the domain logic. Entities of the domain logic are game, user, player_hand,
dealer_hand. Entities used for the database need to be inside the domain logic.
The use case can create instances to be persisted with the factories and pass them to the persistence adapter.
The entities may be known by the persistence adapter, but not by the external resource / Hibernate.

The Services "uses" the interface of the outbound port to request information from the repository adapter.
The repository adapter implements the outbound port interface.

Inbound adapters consist of REST controllers that expose the service functionality via HTTP endpoints.
Outbound adapters interact with a PostgreSQL database via a repository interface that abstracts the persistence mechanism.