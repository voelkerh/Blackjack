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
     * If hand contains an ACE and has total value > 21, ACE counts as 1 instead of 11.
     *
     * @return total hand value
     */
    int getTotal();
}
