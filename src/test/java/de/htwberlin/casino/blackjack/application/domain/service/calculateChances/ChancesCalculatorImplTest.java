package de.htwberlin.casino.blackjack.application.domain.service.calculateChances;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Rank;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Suit;
import de.htwberlin.casino.blackjack.application.domain.model.hands.DealerHand;
import de.htwberlin.casino.blackjack.application.domain.model.hands.PlayerHand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChancesCalculatorImplTest {

    DealerHand dealerHand;

    @BeforeEach
    void setUp() {
        Card dealerUpCard = new Card(Rank.FIVE, Suit.SPADES);
        dealerHand = new DealerHand(dealerUpCard);
    }

    @Test
    public void givenPlayerFiveTwo_whenCalculateChances_thenReturnNotNull() {
        Card card1 = new Card(Rank.FIVE, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.CLUBS);
        PlayerHand playerHand = new PlayerHand(card1, card2);
        ChancesCalculatorImpl calculator = new ChancesCalculatorImpl();

        Chances actual = calculator.calculateChances(playerHand, dealerHand);

        assertNotNull(actual);
    }

    @Test
    public void givenPlayerThreeTwo_whenCalculateChances_thenReturnInstanceOfChances() {
        Card card1 = new Card(Rank.THREE, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.CLUBS);
        PlayerHand playerHand = new PlayerHand(card1, card2);
        ChancesCalculatorImpl calculator = new ChancesCalculatorImpl();

        Chances actual = calculator.calculateChances(playerHand, dealerHand);

        assertInstanceOf(Chances.class, actual);
    }

    @Test
    public void givenPlayerTwoThree_whenCalculateChances_thenReturnBustChancesZero() {
        Card card1 = new Card(Rank.TWO, Suit.CLUBS);
        Card card2 = new Card(Rank.THREE, Suit.CLUBS);
        PlayerHand playerHand = new PlayerHand(card1, card2);
        ChancesCalculatorImpl calculator = new ChancesCalculatorImpl();

        double expected = 0.0;
        double actual = calculator.calculateChances(playerHand, dealerHand).bust();

        assertEquals(expected, actual);
    }

    @Test
    public void givenPlayerTwoThree_whenCalculateChances_thenReturnBlackJackChancesZero() {
        Card card1 = new Card(Rank.TWO, Suit.CLUBS);
        Card card2 = new Card(Rank.THREE, Suit.CLUBS);
        PlayerHand playerHand = new PlayerHand(card1, card2);
        ChancesCalculatorImpl calculator = new ChancesCalculatorImpl();

        double expected = 0.0;
        double actual = calculator.calculateChances(playerHand, dealerHand).blackjack();

        assertEquals(expected, actual);
    }

    @Test
    public void givenPlayerTotalTwenty_whenCalculateChances_thenReturnBustChancesOne() {
        Card card1 = new Card(Rank.TEN, Suit.CLUBS);
        Card card2 = new Card(Rank.TEN, Suit.HEARTS);
        PlayerHand playerHand = new PlayerHand(card1, card2);
        ChancesCalculatorImpl calculator = new ChancesCalculatorImpl();

        double expected = 1.0;
        double actual = calculator.calculateChances(playerHand, dealerHand).bust();

        assertEquals(expected, actual);
    }

    @Test
    public void givenPlayerTotalTwenty_whenCalculateChances_thenReturnBlackJackChancesZero() {
        Card card1 = new Card(Rank.TEN, Suit.CLUBS);
        Card card2 = new Card(Rank.TEN, Suit.HEARTS);
        PlayerHand playerHand = new PlayerHand(card1, card2);
        ChancesCalculatorImpl calculator = new ChancesCalculatorImpl();

        double expected = 0.0;
        double actual  = calculator.calculateChances(playerHand, dealerHand).blackjack();

        assertEquals(expected, actual);
    }

}