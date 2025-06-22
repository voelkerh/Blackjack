# API Endpoints

# Game Interactions
- api/blackjack/play/...

- api/blackjack/play/start
POST
{
    "userID": "1234",
    "bet": "1"
}

Response
{
    "userID": "1234",
    "bet": "1",
    "player_hand": ["card1", "card2"],
    "dealer_hand": ["card1"]
}

- api/blackjack/play/action
POST
{
  "userID": "1234",
  "bet": "1",
  "gamestate": {
        "player_hand": ["card1", "card2"],
        "dealer_hand": ["card1"]
        "double_down": "False"
  },
  "action": "hit" / "stand" (/ "surrender" / "split" / "double_down")
}

# Chances
- api/blackjack/chances

- api/blackjack/chances/{state}
- Rückgabe: Konkrete Gewinnchancen für hit() und stand()

# Statistics
- api/blackjack/stats/...

- GET /api/blackjack/stats/user/{userId}
  {
  "userId": "1234",
  "gamesPlayed": 40,
  "totalBet": 120,
  "totalWon": 95,
  "totalLost": 25,
  "netResult": -25
  }

- GET /api/blackjack/stats/overview
  {
  "totalGames": 2500,
  "totalPlayers": 320,
  "totalBet": 5400,
  "totalWon": 4100,
  "totalLost": 1300,
  "houseProfit": 1300
  }

# Rules
- api/blackjack/rules/..

- api/blackjack/rules/game/{state}
- api/blackjack/rules/general
