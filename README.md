# README

This repository contains a microservice with a RESTful API for the game Blackjack.

![Swagger UI](/docs/screenshots/swagger_ui.png "RESTful API via Swagger UI")

### Play the Game

1. Clone the repository.
2. cd into the project root.
3. Start docker container with `docker compose up --build`.
4. Access Swagger UI: http://localhost:8080/swagger-ui/index.html
5. Play using HTTP-requests. For detailed instructions, please see below.

### Implementation

The project is implemented in Java 21 with Maven as a Spring Boot application with Jakarta Persistence. Where possible, we use Lombok annotations for getters, setters and constructors. The tests are based on JUnit 5 and Mockito as the mocking framework. The diagrams were created with PlantUML.

The implementation follows the hexagonal architecture (also known as ‘ports and adapters’). The implementation comprises a REST controller with differentiated controller classes as inbound adapters. The outbound adapter is based on Jakarta Persistence and communicates with a PostgreSQL database. Four use cases with service classes are implemented in the ‘hexagon’, i.e. the application core. This structure covers the use cases of retrieving rules, playing games, calculating odds and retrieving statistics. The project is containerised with Docker. The implementation adheres to the principles of YAGNI, KISS, SOLID, and DRY. The code is documented using JavaDoc. 

![Components](/docs/uml/component_diagram.drawio.png "Components")

For the design decisions made, see: Tom Hombergs, Clean Architecture. Practical guide to clean software architecture and maintainable code, mitp 2024.

## Instructions and Background Information

The game is based on a simplified version of blackjack.
It can be played using HTTP requests via the API.

### Rules

The player tries to beat the dealer. They try to get the value of their hand closer to 21 without going over or pushing the dealer over.

The following applies to the cards:
- Cards 2-10 correspond to their card value
- Jacks, queens and kings count as 10
- Aces can be 1 or 11 (at the player's discretion)
- Aces can be 1 or 11 (at the player's discretion)

Setup:
- 1 player, 1 dealer
- 52-card deck without jokers, shuffled
- Starting hand:
- Player receives two cards
- Dealer receives one face-up and one face-down card
- Player places their bet

![Game states](/docs/screenshots/state_diagram.png "State diagram for blackjack service")

Gameplay:
- If the player starts with a hand of 21 (ace + jack/queen/king), they have ‘blackjack’.
- The player decides to ‘hit’ or ‘stand’.
- Hit: draw another card.
      -> If the player's hand exceeds 21, they go bust and lose.
- Stand: keep the current hand.

- The player chooses ‘hit’ until they choose ‘stand’.

- Dealer reveals face-down card
  - Aces count as 11 if the dealer is not ‘bust’
        --> If the dealer's hand exceeds 21, they are bust and the player wins.
        --> If the dealer has blackjack, the player loses unless they also have blackjack.
      - If the dealer's hand is < 17 - ‘hit’
  - If the dealer's hand is >= 17, the round ends (unless it is an ace - soft 17, the dealer continues to hit)

- The winner of the round is determined:
  - Whoever is closer to 21 wins
  - If the player's hand and the dealer's hand have the same value - ‘push’

Payout distribution: - Player and dealer have blackjack: player gets their stake back (‘push’)
- Player wins: paid out at a ratio of 1:1 - Player loses: loses their stake
- Player has blackjack (and dealer does not): paid out at a ratio of 2:3

Simplification: no split, no double, no surrender, no side betting (insurance)

### Play via the API

Playing:
- Start a new game: POST /api/blackjack/play,
  Body: { ‘userId’: ‘...’, ‘bet’: ... }
- Perform game moves:
  * Draw card (hit):
  PATCH /api/blackjack/play/{gameId}/hit
  * Hold hand (stand):
  PATCH /api/blackjack/play/{gameId}/stand
- Query current status of a game:
  GET /api/blackjack/play/{gameId}

Retrieve rules:
- General rules: GET /api/blackjack/rules
- Action-specific rules: GET /api/blackjack/rules/{action}, possible values: HIT, STAND

Retrieve odds for currently possible moves in a running game:
- GET /api/blackjack/chances/{gameId}

Retrieve statistics:
- General statistics: GET /api/blackjack/stats/overview
- User-specific statistics: GET /api/blackjack/stats/user/{userId}

**Note:**  
The URL structure `stats/user/{userId}` was deliberately chosen to enable possible future extensions such as `stats/game/{gameId}` and to keep the API consistent and expandable.

![Play via API](/docs/screenshots/api_hit.png "Play via the API")

### Calculation of Chances

For each game state, it is possible to calculate
the probability of a blackjack or a bust when drawing the next card.

The following formulas were used:

m := 1 (number of card decks used)
x (value of the card for which the probability of appearance in the next turn is determined)
N = (total number of cards already drawn that are now in the player's or dealer's hand)
n(x) (number of cards with value x that have already been drawn and are now in the player's or dealer's hand)
P := probability

If x ≠ 10: P(x) = [4m - n(x)] / [52m - N]
If x == 10: P(10) = [16m - n(10)] / [52m - N]

We determine P(bj) and P(bu).
bj (x-value required for a blackjack)
bu ({x | x > bj}, card values for which a bust would occur on the next turn)

bj = 21 - playerHandTotal
P(bj) can be determined directly using the above formulas.

For P(bu), we add up all the probabilities for cards that trigger a bust.
P(bu) = P(x1) + ... + P(xi)

See also: https://probability.infarom.ro/blackjack.html.

### Calcuation of Statistics

The statistical data provides an overview of the gaming behaviour of either an individual user or all players of the blackjack service.

**Note:**
Only completed games (status: BLACKJACK, WON, LOST, PUSH) are taken into account. Ongoing games (status: PLAYING) are excluded.

Benutzerspezifische Statistik
User-specific statistics
- gamesPlayed: Number of completed games played by the user
- winRatio: Win ratio in the format blackjack:won:lost:push
Example: 3:12:5:2 means 3 blackjacks, 12 wins, 5 losses and 2 ties
- totalBet: Total amount of all bets placed by the user
- netResult: Net profit or loss of the user.
  Calculation: Payouts – bets

    Payout logic per game:
    * BLACKJACK: Payout = 2.5 × stake
    * WON: Payout = 2 × stake
    * PUSH: Payout = 1 × stake (no win or loss)
    * LOST: No payout (0)

General statistics
- totalGames: Total number of games completed by all players
- totalPlayers: Number of different users who have completed at least one game
- totalBet: Total amount wagered by all users in completed games.
- houseProfit: Profit for the ‘house’, calculated as the difference between wagers and payouts.

  Calculation logic for house profit:
  * BLACKJACK: House pays out increased profit (−1.5 × stake)
  * WON: House pays out profit (−stake)
  * PUSH: neutral (neither profit nor loss for the house)
  * LOST: House wins the stake (+stake)

## License

Blackjack Micro-Service by Johanna Menzler and Henriette Voelker is marked CC0 1.0.

The project is a coursework assignment for the Software Engineering and Software Architectures course. The authors are Johanna Menzler and Henriette Voelker.