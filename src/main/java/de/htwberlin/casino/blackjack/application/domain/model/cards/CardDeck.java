package de.htwberlin.casino.blackjack.application.domain.model.cards;

import java.util.List;

/**
 * Defines valid interactions with the card deck during a game of blackjack.
 */
public interface CardDeck {

    /**
     * Draws the upmost card from card deck.
     * Returns a card to be added to a hand and removes it from card deck list.
     *
     * @return Card drawn and removed from card deck
     */
    Card drawCard();

    /**
     * Removes all dealt cards included in player and dealer hand from card deck.
     *
     * @param dealtCards list constructed from player and dealer hand in services
     * @return boolean indicating success or failure of removal
     */
    boolean removeDealtCards(List<Card> dealtCards);

}
