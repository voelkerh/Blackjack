package de.htwberlin.casino.blackjack.application.domain.factory.hands;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.hands.DealerHand;
import de.htwberlin.casino.blackjack.application.domain.model.hands.Hand;
import de.htwberlin.casino.blackjack.application.domain.model.hands.HandType;
import de.htwberlin.casino.blackjack.application.domain.model.hands.PlayerHand;

import java.util.List;

/**
 * A concrete implementation of the {@link HandFactory} interface.
 * <p>
 * Note: This class is not 100% unit tested because the {@code default} branch in the
 * {@code switch} statement (which throws {@link IllegalArgumentException} for unsupported
 * {@link HandType}s) cannot be triggered without introducing an additional enum constant,
 * which is intentionally avoided to preserve the integrity of the domain model.
 */
public class HandFactoryImpl implements HandFactory {

    @Override
    @SuppressWarnings("unchecked") // type casting is done on purpose and controlled
    public <T extends Hand> T create(HandType type, List<Card> cards) {
        if (cards == null || type == null) throw new IllegalArgumentException("Parameters cannot be null");
        switch (type) {
            case DEALER:
                if (cards.size() < 2)
                    throw new IllegalArgumentException("DealerHand can only be initialized with more than one card");
                else if (cards.size() == 2) return (T) new DealerHand(cards.get(0), cards.get(1));
                DealerHand dealerHand = new DealerHand(cards.get(0), cards.get(1));
                for (int i = 2; i < cards.size(); i++) {
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
