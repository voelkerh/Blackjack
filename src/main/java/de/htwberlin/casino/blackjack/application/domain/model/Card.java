package de.htwberlin.casino.blackjack.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Card {

    private final Rank rank;
    private final Suit suit;
}
