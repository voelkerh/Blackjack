package de.htwberlin.casino.blackjack.application.domain.model.hands;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Rank;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the dealer's hand in a blackjack game.
 */
@Getter
public class DealerHand implements Hand {

    private final List<Card> cards;

    /**
     * Constructor initializing dealerHand with card drawn upon game initialization.
     * @param upCard First card drawn by dealer at initialization of game.
     */
    public DealerHand(Card upCard) {
        if (upCard == null) throw new NullPointerException();
        this.cards = new ArrayList<>();
        cards.add(upCard);
    }

    /**
     * Show single visible card held by dealer during player's turn.
     * @return Card
     */
    public Card getUpCard() {
        return cards.getFirst();
    }

    @Override
    public boolean addCard(Card card) {
        if (card == null) return false;
        return cards.add(card);
    }

    @Override
    public List<Card> getCards() {
        return List.copyOf(cards);
    }

    @Override
    public int getTotal() {
        int total = cards.stream()
                .mapToInt(card -> card.rank().getValue())
                .sum();
        long numberOfAces = cards.stream()
                .filter(card -> card.rank() == Rank.ACE)
                .count();
        while (numberOfAces > 0 && total > 21) {
            total -= 10;
            numberOfAces--;
        }
        return total;    }

}
