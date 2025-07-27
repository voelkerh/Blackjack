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
public class HandFactoryImpl implements HandFactory {

    @Override
    @SuppressWarnings("unchecked") // type casting is done on purpose and controlled
    public <T extends Hand> T create(HandType type, List<Card> cards) {
        if (cards == null || type == null) throw new IllegalArgumentException("Parameters cannot be null");
        switch (type) {
            case DEALER:
                if (cards.isEmpty()) {
                    throw new IllegalArgumentException("DealerHand must be initialized with at least one card");
                }
                DealerHand dealerHand = new DealerHand(cards.getFirst());
                for (int i = 1; i < cards.size(); i++) {
                    dealerHand.addCard(cards.get(i));
                }
                return (T) dealerHand;
            case PLAYER:
                if (cards.size() < 2)
                    throw new IllegalArgumentException("Playerhand can only be initialized with more than one card");
                else if (cards.size() == 2) return (T) new PlayerHand(cards.get(0), cards.get(1));
                PlayerHand playerHand = new PlayerHand(cards.get(0), cards.get(1));
                for (int i = 2; i < cards.size(); i++) {
                    playerHand.addCard(cards.get(i));
                }
                return (T) playerHand;
            default:
                throw new IllegalArgumentException("Hand type not supported");
        }
    }
}
