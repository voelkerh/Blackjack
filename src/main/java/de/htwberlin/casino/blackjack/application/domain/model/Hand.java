package de.htwberlin.casino.blackjack.application.domain.model;

import java.util.List;

public interface Hand {

    /**
     * Adds card to the list of cards held in hand.
     *
     * @param card
     */
    void addCard(Card card);

    /**
     * Returns a copy of the cards held in hand.
     *
     * @return List of cards held in hand
     */
    List<Card> getCards();

    /**
     * Calculates total hand value.
     * Player and dealer hands handle ACES differently.
     * Player: If hand contains ACES and has total value > 21, ACE counts as 1 instead of 11 until total value= < 21.
     * Dealer: ACE counts as 11.
     *
     * @return total hand value
     */
    int getTotal();
}
