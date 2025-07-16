package de.htwberlin.casino.blackjack.application.domain.factory.cards;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.cards.CardDeck;
import de.htwberlin.casino.blackjack.application.domain.model.cards.CardDeckImpl;

import java.util.List;

/**
 * A concrete implementation of card deck factory methods.
 */
public class CardDeckFactoryImpl implements CardDeckFactory {

    @Override
    public CardDeck fromScratch() {
        return new CardDeckImpl();
    }

    @Override
    public CardDeck fromDrawnCards(List<Card> drawnCards) {
        return new CardDeckImpl(drawnCards);
    }
}
