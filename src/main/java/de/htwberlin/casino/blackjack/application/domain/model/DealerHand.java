package de.htwberlin.casino.blackjack.application.domain.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DealerHand implements Hand {

    private final List<Card> cards;

    public DealerHand(Card upCard) {
        this.cards = new ArrayList<>();
        cards.add(upCard);
    }

    public Card getUpCard() {
        return cards.getFirst();
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
        return cards.stream().mapToInt(card -> card.rank().getValue()).sum();
    }

}
