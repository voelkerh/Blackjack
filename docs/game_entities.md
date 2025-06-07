Player:
- Hand
- bet
- userID

Dealer:
- Hand

Hand:
- cards
- calculateValue
- isBlackJack
- isBust

Card (record Klasse):
- Suit (clubs, diamonds, hearts, spades - enum)
- Rank (two, ..., king, ace - enum)

Deck:
- cards
- shuffle
- draw

Game:
- Player
- Dealer
- Deck
- gamephase (initial, player_turn, dealer_turn, final)
- result (player_wins, dealer_wins, push, unfinished)