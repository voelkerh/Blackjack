package de.htwberlin.casino.blackjack.application.domain.service.playGame;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameState;
import de.htwberlin.casino.blackjack.application.domain.model.hands.Hand;

import java.util.List;

/**
 * Defines requirements for play game implementations.
 */
public interface PlayGame {

    /**
     * Determines whether the given hand qualifies as an initial blackjack
     * (i.e: first 2 drawn cards add to exactly 21).
     *
     * @param hand the {@link Hand} to evaluate
     * @return {@code true} if the hand is an initial blackjack; {@code false} otherwise
     */
    boolean handHasInitialBlackjack(Hand hand);

    /**
     * Checks if the player has busted (i.e. total hand value exceeds 21).
     *
     * @param game the current {@link Game} state
     * @return {@code true} if the player's hand total is greater than 21; {@code false} otherwise
     */
    boolean isPlayerBusted(Game game);

    /**
     * Executes the player's turn by drawing a card from the deck.
     * <p>
     * Note: This method only returns the drawn card — it is the caller’s responsibility
     * to persist the card in the player's hand.
     *
     * @param game the current {@link Game} state
     * @return the {@link Card} drawn from the deck
     */
    Card playPlayerTurn(Game game);

    /**
     * Executes the dealer's turn by drawing cards until the hand total reaches at least 17.
     * <p>
     * All drawn cards are returned in order and must be persisted by the caller.
     *
     * @param game the current {@link Game} state
     * @return a list of {@link Card}s drawn by the dealer during the turn
     */
    List<Card> playDealerTurn(Game game);

    /**
     * Determines the final outcome of the game based on the player's and dealer's hands.
     * <p>
     * The result can be:
     * <ul>
     *     <li>{@link GameState#WON} – if the player wins</li>
     *     <li>{@link GameState#LOST} – if the player loses</li>
     *     <li>{@link GameState#PUSH} – if the game is a tie</li>
     * </ul>
     *
     * @param playerHand the final state of the player's hand
     * @param dealerHand the final state of the dealer's hand
     * @return the resulting {@link GameState} of the game
     */
    GameState determineResult(Hand playerHand, Hand dealerHand);
}
