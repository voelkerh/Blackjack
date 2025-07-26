package de.htwberlin.casino.blackjack.application.domain.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class PlayerHand implements Hand {

    private final List<Card> cards;

    public PlayerHand(Card card1, Card card2) {
        this.cards = Arrays.asList(card1, card2);
    }

    public PlayerHand(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public void addCard(Card card) {
        cards.add(card);
    }

    @Override
    public List<Card> getCards() {
        return List.copyOf(cards);
    }
}
