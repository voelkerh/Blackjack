package de.htwberlin.casino.blackjack.application.domain.model.game;

import de.htwberlin.casino.blackjack.application.domain.model.cards.CardDeckImpl;
import de.htwberlin.casino.blackjack.application.domain.model.hands.DealerHand;
import de.htwberlin.casino.blackjack.application.domain.model.hands.PlayerHand;
import lombok.Getter;

/**
 * Implementation of the game representing an ongoing game of blackjack.
 */
@Getter
public class GameImpl implements Game {

    private final Long gameId;
    private final String userId;
    private CardDeckImpl cardDeck;
    private PlayerHand playerHand;
    private DealerHand dealerHand;
    private GameState gameState;
    private final double bet;

    public GameImpl(Long gameId, String userId, CardDeckImpl cardDeck,
                PlayerHand playerHand, DealerHand dealerHand, GameState gameState, double bet) {
        this.gameId = gameId;
        this.userId = userId;
        this.cardDeck = cardDeck;
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
        this.gameState = gameState;
        this.bet = bet;
    }

    public GameImpl(Long gameId, String userId, double bet) {
        this.gameId = gameId;
        this.userId = userId;
        this.bet = bet;
        this.initialize();
    }

    /**
     * Initializes a new game by drawing cards for player and dealer hand and setting gameState to playing.
     */
    public void initialize() {
        playerHand = new PlayerHand(cardDeck.drawCard(), cardDeck.drawCard());
        dealerHand = new DealerHand(cardDeck.drawCard());
        gameState = GameState.PLAYING;
    }

}
