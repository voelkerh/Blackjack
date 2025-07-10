package de.htwberlin.casino.blackjack.application.domain.model;

import java.util.List;

public interface Hand {

    void addCard(Card card);

    List<Card> getCards();
}
