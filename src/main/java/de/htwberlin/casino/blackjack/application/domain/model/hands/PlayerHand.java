package de.htwberlin.casino.blackjack.application.domain.model.hands;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Rank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public class PlayerHand implements Hand {

    private final List<Card> cards;

    public PlayerHand(Card card1, Card card2) {
        if (card1 == null || card2 == null) throw new NullPointerException();
        this.cards = new ArrayList<>(Arrays.asList(card1, card2));
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
        return total;
    }
}
