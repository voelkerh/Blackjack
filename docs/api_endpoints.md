# API Endpoints

# Game Interactions
- api/blackjack/play/...

- POST /api/blackjack/play (starts a new blackjack game)
{
    "userID": "1234",
    "bet": "1"
}

Response
{
    "userID": "1234",
    "bet": "1",
    "gameId" : "abc123",
    "gameState": "PLAYING", // or "WON", "LOST", "PUSH"
    "playerHand": [{ "suit": "spades", "rank": "10" }, { "suit": "hearts", "rank": "A" }],
    "dealerHand": [{ "suit": "clubs", "rank": "A" }]     // only face-up card at starting point
    "playerTotal": 21,
    "dealerTotal": null // include only once the game is over
}

- PUT api/blackjack/play/{gameId}/hit
- PUT api/blackjack/play/{gameId}/stand

Response
{
    "gameId": "abc123",
    "gameState": "PLAYING", // or "WON", "LOST", "PUSH"
    "playerHand": [
    { "suit": "hearts", "rank": "K" },
    { "suit": "clubs", "rank": "A" }
    ],
    "dealerHand": [{ "suit": "spades", "rank": "9" }],
    "playerTotal": 21,
    "dealerTotal": null,
    "bet": 50
}

- GET api/blackjack/play/{gameId}
Response
{
  "gameId": "abc123",
  "gameState": "PLAYING", // or "WON", "LOST", "PUSH"
  "playerHand": [
  { "suit": "hearts", "rank": "K" },
  { "suit": "clubs", "rank": "A" }
  ],
  "dealerHand": [{ "suit": "spades", "rank": "9" }],
  "playerTotal": 21,
  "dealerTotal": null,
  "bet": 50
}


# Chances
- api/blackjack/chances

- api/blackjack/chances/{state}
- Rückgabe: Konkrete Gewinnchancen für hit() und stand()

# Statistics
- api/blackjack/stats/...

- GET /api/blackjack/stats/user/{userId}
  {
  "gamesPlayed": 40,
  "winRatio": "1:100:2" (win, loss, tie)
  "totalBet": 120,
  "netResult": -25
  }

- GET /api/blackjack/stats/overview
  {
  "totalGames": 2500,
  "totalPlayers": 320,
  "totalBet": 5400,
  "houseProfit": 1300
  }

# Rules
- api/blackjack/rules/..

- api/blackjack/rules/game/{state}
- api/blackjack/rules/general
