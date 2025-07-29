package de.htwberlin.casino.blackjack.application.domain.model.game;

import de.htwberlin.casino.blackjack.application.domain.model.cards.CardDeck;
import de.htwberlin.casino.blackjack.application.domain.model.hands.Hand;

/**
 * Domain abstraction representing a game of Blackjack.
 * <p>
 * Provides read access to all relevant game state including the player and dealer hands,
 * the current deck, the associated user, game ID, and the amount of the bet placed.
 */
public interface Game {
    /**
     * @return the unique identifier of the game, may be {@code null} if the game has not been persisted yet
     */
    Long getId();

    /**
     * @return ID of the user who initiated the game
     */
    String getUserId();

    /**
     * @return current {@link GameState}
     */
    GameState getGameState();

    void setGameState(GameState gameState);

    /**
     * @return {@link CardDeck} instance used in this game
     */
    CardDeck getCardDeck();

    /**
     * @return player's {@link Hand}
     */
    Hand getPlayerHand();

    /**
     * @return dealer's {@link Hand}
     */
    Hand getDealerHand();

    /**
     * @return bet amount for this game
     */
    double getBet();
}
