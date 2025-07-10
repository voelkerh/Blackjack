package de.htwberlin.casino.blackjack.application.domain.model;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class DealerHand implements Hand {

    private final Card upCard;
    private final List<Card> cards;

    public DealerHand(Card upCard) {
        this.upCard = upCard;
        this.cards = new ArrayList<>();
        cards.add(upCard);
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
