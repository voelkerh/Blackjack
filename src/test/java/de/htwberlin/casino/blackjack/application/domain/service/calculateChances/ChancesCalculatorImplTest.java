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
    public void givenPlayerThreeTwo_whenCalculateChances_thenReturnValidBlackjackChances() {
        Card card1 = new Card(Rank.THREE, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.CLUBS);
        PlayerHand playerHand = new PlayerHand(card1, card2);
        ChancesCalculatorImpl calculator = new ChancesCalculatorImpl();

        Chances actual = calculator.calculateChances(playerHand, dealerHand);

        assertTrue(actual.blackjack() < 0.1);
    }

}