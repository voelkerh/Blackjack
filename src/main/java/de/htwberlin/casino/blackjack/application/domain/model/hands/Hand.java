package de.htwberlin.casino.blackjack.application.domain.model.hands;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;

import java.util.List;

/**
 * Defines interactions with player and dealer hand.
 */
public interface Hand {

    /**
     * Adds card to the list of cards held in hand.
     *
     * @param card Card to be added
     * @return whether adding card was successful
     */
    boolean addCard(Card card);

    /**
     * Returns a copy of the cards held in hand.
     *
     * @return List of cards held in hand
     */
    List<Card> getCards();

    /**
     * Calculates total hand value.
     * The behavior may differ depending on the implementing class (PlayerHand or DealerHand).
     *
     * @return total hand value
     */
    int getTotal();

}
