# API Endpoints

# Game Interactions
- api/blackjack/play/...

- /api/blackjack/play
POST
  {
    "userID": "1234",
    "bet": "1"
}

Response
{
    "userID": "1234",
    "bet": "1",
    "gameId" : "abc123"
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

Response
{
  "gameId": "abc123",
  "playerHand": [
  { "suit": "hearts", "rank": "K" },
  { "suit": "clubs", "rank": "A" }
  ],
  "dealerHand": [{ "suit": "spades", "rank": "9" }],
  "playerTotal": 21,
  "dealerTotal": null,
  "status": "PLAYING", // or "WON", "LOST", "PUSH"
  "bet": 50
  }

# Chances
- api/blackjack/chances

- api/blackjack/chances/{state}
- Rückgabe: Konkrete Gewinnchancen für hit() und stand()

# Statistics
- api/blackjack/stats/...
- (GET) (POST)

- api/blackjack/stats/user/{userId}
- api/blackjack/stats/general

# Rules
- api/blackjack/rules/..

- api/blackjack/rules/game/{state}
- api/blackjack/rules/general
