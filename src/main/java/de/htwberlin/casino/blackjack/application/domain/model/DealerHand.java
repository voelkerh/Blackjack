package de.htwberlin.casino.blackjack.application.domain.model;

import lombok.Getter;

@Getter
public class DealerHand {

    private final Card upCard;

    public DealerHand(Card upCard) {
        this.upCard = upCard;
    }

}
