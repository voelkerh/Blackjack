package de.htwberlin.casino.blackjack.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public class PlayerHand implements Hand {

    private final List<Card> cards;

    public PlayerHand(Card card1, Card card2) {
        this.cards = Arrays.asList(card1, card2);
    }

    @Override
    public void addCard(Card card) {
        cards.add(card);
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
        while (numberOfAces > 0 && total >= 21) {
            total -= 10;
            numberOfAces--;
        }
        return total;
    }
}
