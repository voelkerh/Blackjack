package de.htwberlin.casino.blackjack.application.domain.model;

import lombok.Getter;

@Getter
public class Game {

    private final Long gameId;
    private final Long userId;
    private CardDeck cardDeck;
    private PlayerHand playerHand;
    private DealerHand dealerHand;
    private GameState gameState;
    private final double bet;

    public Game(Long gameId, Long userId, CardDeck cardDeck,
                PlayerHand playerHand, DealerHand dealerHand, GameState gameState, double bet) {
        this.gameId = gameId;
        this.userId = userId;
        this.cardDeck = cardDeck;
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
        this.gameState = gameState;
        this.bet = bet;
    }

    public Game(Long gameId, Long userId, double bet) {
        this.gameId = gameId;
        this.userId = userId;
        this.bet = bet;
        this.initialize();
    }

    public void initialize() {
        cardDeck = CardDeck.getInstance();
        playerHand = new PlayerHand(cardDeck.drawCard(), cardDeck.drawCard());
        dealerHand = new DealerHand(cardDeck.drawCard());
        gameState = GameState.PLAYING;
    }

}
