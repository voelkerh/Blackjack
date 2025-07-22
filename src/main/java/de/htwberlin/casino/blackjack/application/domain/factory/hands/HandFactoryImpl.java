package de.htwberlin.casino.blackjack.application.domain.factory.hands;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.hands.DealerHand;
import de.htwberlin.casino.blackjack.application.domain.model.hands.Hand;
import de.htwberlin.casino.blackjack.application.domain.model.hands.HandType;
import de.htwberlin.casino.blackjack.application.domain.model.hands.PlayerHand;

import java.util.List;

/**
 * A concrete implementation of hand factory methods.
 */
public class HandFactoryImpl implements  HandFactory {

    @Override
    public Hand create(HandType type, List<Card> cards) {
        if (cards == null || type == null) throw new IllegalArgumentException("Parameters cannot be null");
        switch (type) {
            case DEALER:
                if (cards.size() != 1) throw new IllegalArgumentException("Dealerhand can only be initialized with one card");
                return new DealerHand(cards.get(0));
            case PLAYER:
                if (cards.size() < 2) throw new IllegalArgumentException("Playerhand can only be initialized with more than one card");
                else if (cards.size() == 2) return new PlayerHand(cards.get(0), cards.get(1));
                PlayerHand playerHand = new PlayerHand(cards.get(0), cards.get(1));
                for (int i = 2; i < cards.size(); i++) {
                    playerHand.addCard(cards.get(i));
                }
                return playerHand;
            default:
                throw new IllegalArgumentException("Hand type not supported");
        }
    }
}
