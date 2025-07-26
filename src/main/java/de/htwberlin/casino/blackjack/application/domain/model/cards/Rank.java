package de.htwberlin.casino.blackjack.application.domain.model.cards;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Rank used to identify a playing card.
 */
@RequiredArgsConstructor
@Getter
public enum Rank {
    TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
    JACK(10), QUEEN(10), KING(10), ACE(11);


    private final int value;

    /**
     * Creates sample rank from given numeric value.
     * Used to reverse create playing cards, e.g. for chances calculation.
     *
     * @param value of a playing card
     * @return Rank
     */
    public static Rank fromValue(int value) {
        return switch (value) {
            case 2 -> Rank.TWO;
            case 3 -> Rank.THREE;
            case 4 -> Rank.FOUR;
            case 5 -> Rank.FIVE;
            case 6 -> Rank.SIX;
            case 7 -> Rank.SEVEN;
            case 8 -> Rank.EIGHT;
            case 9 -> Rank.NINE;
            case 10 -> Rank.TEN;
            case 11 -> Rank.ACE;
            default -> throw new IllegalArgumentException("Invalid card value: " + value);
        };
    }

}
