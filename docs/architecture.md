# Architecture

## Choice

Hexagonale Architektur
Check https://www.happycoders.eu/de/software-craftsmanship/hexagonale-architektur/

## Transfer to Blackjack Use Case

Client: HTTP-requests
Controller: REST-Adapter, incoming, handle HTTP-request
UseCase: Input-Port, orchestrator
ServiceImpl: Domain logic