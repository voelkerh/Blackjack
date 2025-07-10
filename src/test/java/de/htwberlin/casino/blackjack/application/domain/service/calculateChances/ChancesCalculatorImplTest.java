package de.htwberlin.casino.blackjack.application.domain.service.calculateChances;

import de.htwberlin.casino.blackjack.application.domain.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChancesCalculatorImplTest {

    @Test
    public void givenPlayerFiveTwoDealerThree_whenCalculateChances_thenReturnNotNull() {
        Card card1 = new Card(Rank.FIVE, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.CLUBS);
        Card card3 = new Card(Rank.THREE, Suit.CLUBS);
        PlayerHand playerHand = new PlayerHand(card1, card2);
        DealerHand dealerHand = new DealerHand(card3);
        ChancesCalculatorImpl calculator = new ChancesCalculatorImpl();

        Chances actual = calculator.calculateChances(playerHand, dealerHand);

        assertNotNull(actual);
    }

    @Test
    public void givenPlayerFiveTwoDealerThree_whenCalculateChances_thenReturnCorrectChances() {
        Card card1 = new Card(Rank.FIVE, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.CLUBS);
        Card card3 = new Card(Rank.THREE, Suit.CLUBS);
        PlayerHand playerHand = new PlayerHand(card1, card2);
        DealerHand dealerHand = new DealerHand(card3);
        ChancesCalculatorImpl calculator = new ChancesCalculatorImpl();

        Chances actual = calculator.calculateChances(playerHand, dealerHand);
        double expectedBlackJack = 0.3;
        double expectedBust = 0.5;

        assertEquals(expectedBlackJack, actual.blackjack());
        assertEquals(expectedBust, actual.bust());
    }

}