package de.htwberlin.casino.blackjack.application.domain.model.game;

import de.htwberlin.casino.blackjack.application.domain.model.cards.CardDeckImpl;
import de.htwberlin.casino.blackjack.application.domain.model.hands.DealerHand;
import de.htwberlin.casino.blackjack.application.domain.model.hands.PlayerHand;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Concrete implementation of the {@link Game} interface representing an ongoing game of blackjack.
 * <p>
 * Provides constructors to either fully initialize the game externally
 * or initialize it internally with a fresh shuffled deck.
 */
@Getter
@AllArgsConstructor
public class GameImpl implements Game {
    private final Long id;
    private final String userId;
    private CardDeckImpl cardDeck;
    private PlayerHand playerHand;
    private DealerHand dealerHand;
    private GameState gameState;
    private final double bet;
    /**
     * Constructs a new {@code GameImpl} for a fresh game.
     * Initializes the game with a newly shuffled deck and deals initial cards.
     * Automatically sets the game state to {@link GameState#PLAYING}.
     *
     * @param id     game ID, may be {@code null} if not persisted yet
     * @param userId ID of the user playing the game
     * @param bet    the bet amount placed by the player
     */
    public GameImpl(Long id, String userId, double bet) {
        this.id = id;
        this.userId = userId;
        this.bet = bet;
        this.initialize();
    }

    /**
     * Initializes a new game by drawing cards for player and dealer hand and setting gameState to playing.
     */
    private void initialize() {
        cardDeck = new CardDeckImpl();
        playerHand = new PlayerHand(cardDeck.drawCard(), cardDeck.drawCard());
        dealerHand = new DealerHand(cardDeck.drawCard(), cardDeck.drawCard());
        gameState = GameState.PLAYING;
    }

    @Override
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
