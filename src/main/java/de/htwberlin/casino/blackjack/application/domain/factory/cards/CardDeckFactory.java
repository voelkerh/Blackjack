package de.htwberlin.casino.blackjack.application.domain.factory.cards;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.cards.CardDeck;

import java.util.List;

/**
 * An interface defining card deck factory methods.
 */
public interface CardDeckFactory {

    /**
     * Calls constructor for first game initialisation.
     *
     * @return CardDeck
     */
    CardDeck fromScratch();

    /**
     * Calls constructor after game retrieval from database with cards to be removed.
     *
     * @param cards drawn before, which need to be removed from the card deck
     * @return CardDeck
     */
    CardDeck withDrawnCards(List<Card> cards);

}
