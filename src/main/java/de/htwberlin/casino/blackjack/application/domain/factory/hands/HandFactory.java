package de.htwberlin.casino.blackjack.application.domain.factory.hands;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.hands.Hand;
import de.htwberlin.casino.blackjack.application.domain.model.hands.HandType;

import java.util.List;

/**
 * An interface defining hand factory methods.
 */
public interface HandFactory {

    /**
     * Calls constructor for respective hand type.
     *
     * @return created Hand
     */
    Hand create(HandType tye, List<Card> cards);
}
