package de.htwberlin.casino.blackjack.application.domain.model;

import org.springframework.stereotype.Component;

@Component
public record Card(Rank rank, Suit suit) {

}
