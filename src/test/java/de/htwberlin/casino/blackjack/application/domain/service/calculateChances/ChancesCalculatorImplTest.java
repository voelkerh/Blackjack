package de.htwberlin.casino.blackjack.application.domain.service.calculateChances;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Rank;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Suit;
import de.htwberlin.casino.blackjack.application.domain.model.hands.DealerHand;
import de.htwberlin.casino.blackjack.application.domain.model.hands.PlayerHand;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ChancesCalculatorImplTest {

    DealerHand dealerHand;
    ChancesCalculatorImpl calculator;

    @BeforeEach
    void setUp() {
        Card dealerUpCard = new Card(Rank.THREE, Suit.SPADES);
        Card dealerDownCard = new Card(Rank.TWO, Suit.HEARTS);
        dealerHand = new DealerHand(dealerUpCard, dealerDownCard);
        calculator = new ChancesCalculatorImpl();
    }

    @Test
    public void givenPlayerFiveTwo_whenCalculateChances_thenReturnNotNull() {
        Card card1 = new Card(Rank.FIVE, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.CLUBS);
        PlayerHand playerHand = new PlayerHand(card1, card2);

        Chances actual = calculator.calculateChances(playerHand, dealerHand);

        assertNotNull(actual);
    }

    @Test
    public void givenPlayerThreeTwo_whenCalculateChances_thenReturnInstanceOfChances() {
        Card card1 = new Card(Rank.THREE, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.CLUBS);
        PlayerHand playerHand = new PlayerHand(card1, card2);

        Chances actual = calculator.calculateChances(playerHand, dealerHand);

        assertInstanceOf(Chances.class, actual);
    }

    @Test
    public void givenPlayerTwoThree_whenCalculateChances_thenReturnBustChancesZero() {
        Card card1 = new Card(Rank.TWO, Suit.CLUBS);
        Card card2 = new Card(Rank.THREE, Suit.CLUBS);
        PlayerHand playerHand = new PlayerHand(card1, card2);

        double expected = 0.0;
        double actual = calculator.calculateChances(playerHand, dealerHand).bust();

        assertEquals(expected, actual);
    }

    @Test
    public void givenPlayerTwoThree_whenCalculateChances_thenReturnBlackJackChancesZero() {
        Card card1 = new Card(Rank.TWO, Suit.CLUBS);
        Card card2 = new Card(Rank.THREE, Suit.CLUBS);
        PlayerHand playerHand = new PlayerHand(card1, card2);

        double expected = 0.0;
        double actual = calculator.calculateChances(playerHand, dealerHand).blackjack();

        assertEquals(expected, actual);
    }

    @Test
    public void givenPlayerTotalTwenty_whenCalculateChances_thenReturnBustChancesOne() {
        Card card1 = new Card(Rank.TEN, Suit.CLUBS);
        Card card2 = new Card(Rank.TEN, Suit.HEARTS);
        PlayerHand playerHand = new PlayerHand(card1, card2);

        double expected = 1.0;
        double actual = calculator.calculateChances(playerHand, dealerHand).bust();

        assertEquals(expected, actual);
    }

    @Test
    public void givenPlayerTotalTwenty_whenCalculateChances_thenReturnBlackJackChancesZero() {
        Card card1 = new Card(Rank.TEN, Suit.CLUBS);
        Card card2 = new Card(Rank.TEN, Suit.HEARTS);
        PlayerHand playerHand = new PlayerHand(card1, card2);


        double expected = 0.0;
        double actual = calculator.calculateChances(playerHand, dealerHand).blackjack();

        assertEquals(expected, actual);
    }

    @Test
    public void givenPlayerFiveFive_whenCalculateChances_thenReturnBlackJackChancesLow() {
        Card card1 = new Card(Rank.FIVE, Suit.CLUBS);
        Card card2 = new Card(Rank.FIVE, Suit.HEARTS);
        PlayerHand playerHand = new PlayerHand(card1, card2);

        double expected = 0.08;
        double actual = calculator.calculateChances(playerHand, dealerHand).blackjack();
        double delta = 0.01;

        assertEquals(expected, actual, delta);
    }

    @Test
    public void givenPlayerFiveSix_whenCalculateChances_thenReturnBlackJackChancesHigh() {
        Card card1 = new Card(Rank.FIVE, Suit.CLUBS);
        Card card2 = new Card(Rank.SIX, Suit.HEARTS);
        PlayerHand playerHand = new PlayerHand(card1, card2);

        double expected = 0.3333333333333333;
        double actual = calculator.calculateChances(playerHand, dealerHand).blackjack();
        double delta = 0.01;

        assertEquals(expected, actual, delta);
    }

    @Test
    public void givenPlayerSixSix_whenCalculateChances_thenReturnBlackJackChancesLow() {
        Card card1 = new Card(Rank.SIX, Suit.CLUBS);
        Card card2 = new Card(Rank.SIX, Suit.HEARTS);
        PlayerHand playerHand = new PlayerHand(card1, card2);

        double expected = 0.08;
        double actual = calculator.calculateChances(playerHand, dealerHand).blackjack();
        double delta = 0.01;

        assertEquals(expected, actual, delta);
    }

    @Test
    public void givenPlayerSixEight_whenCalculateChances_thenReturnBustChancesLow() {
        Card card1 = new Card(Rank.SIX, Suit.CLUBS);
        Card card2 = new Card(Rank.EIGHT, Suit.HEARTS);
        PlayerHand playerHand = new PlayerHand(card1, card2);

        double expected = 0.47916666666666663;
        double actual = calculator.calculateChances(playerHand, dealerHand).bust();
        double delta = 0.01;

        assertEquals(expected, actual, delta);
    }


}