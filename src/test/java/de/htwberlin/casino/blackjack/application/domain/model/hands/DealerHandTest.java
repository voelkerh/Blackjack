package de.htwberlin.casino.blackjack.application.domain.model.hands;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Rank;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Suit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DealerHandTest {

    @Test
    void givenUpCard_whenCreateDealerHand_returnNotNull() {
        Card upCard = new Card(Rank.ACE, Suit.CLUBS);

        DealerHand dealerHand = new DealerHand(upCard);

        assertNotNull(dealerHand);
    }

    @Test
    void givenUpCardNull_whenCreateDealerHand_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DealerHand(null));
    }

    @Test
    void givenDealerHandAndCard_whenGetUpCard_returnCorrectCard() {
        Card expected = new Card(Rank.ACE, Suit.CLUBS);
        DealerHand dealerHand = new DealerHand(expected);

        Card actual = dealerHand.getUpCard();

        assertEquals(expected, actual);
    }

    @Test
    void givenDealerHandAndNullCard_whenAddCard_returnFalse() {
        Card card1 = new Card(Rank.ACE, Suit.CLUBS);
        DealerHand dealerHand = new DealerHand(card1);

        boolean actual = dealerHand.addCard(null);

        assertFalse(actual);
    }

    @Test
    void givenDealerHandAndActualCard_whenAddCard_returnTrue() {
        Card card1 = new Card(Rank.ACE, Suit.CLUBS);
        DealerHand dealerHand = new DealerHand(card1);
        Card card2 = new Card(Rank.THREE, Suit.HEARTS);

        boolean actual = dealerHand.addCard(card2);

        assertTrue(actual);
    }

    @Test
    void givenDealerHandTwo_whenAddCardTwo_returnTotalFour() {
        Card card1 = new Card(Rank.TWO, Suit.CLUBS);
        DealerHand dealerHand = new DealerHand(card1);
        Card card2 = new Card(Rank.TWO, Suit.HEARTS);

        dealerHand.addCard(card2);

        int expected = 4;
        int actual = dealerHand.getTotal();
        assertEquals(expected, actual);
    }

    @Test
    void givenDealerHandTwoAces_whenAddCardAce_returnTotalTwelve() {
        Card card1 = new Card(Rank.ACE, Suit.CLUBS);
        DealerHand dealerHand = new DealerHand(card1);
        Card card2 = new Card(Rank.ACE, Suit.HEARTS);

        dealerHand.addCard(card2);

        int expected = 12;
        int actual = dealerHand.getTotal();
        assertEquals(expected, actual);
    }

    @Test
    void givenTwoCards_whenGetCards_returnListOfSizeTwo() {
        Card card1 = new Card(Rank.ACE, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.HEARTS);
        DealerHand dealerHand = new DealerHand(card1);
        dealerHand.addCard(card2);

        List<Card> cards = dealerHand.getCards();
        int expected = 2;
        int actual = cards.size();

        assertEquals(expected, actual);
    }

    @Test
    void givenOneCard_whenGetCards_returnCorrectCardInList() {
        Card expected = new Card(Rank.ACE, Suit.CLUBS);
        DealerHand dealerHand = new DealerHand(expected);

        List<Card> cards = dealerHand.getCards();
        Card actual = cards.getFirst();

        assertEquals(expected, actual);
    }

    @Test
    void givenDealerHandWithTotalThree_whenGetTotal_returnCorrectValue() {
        Card card1 = new Card(Rank.THREE, Suit.CLUBS);
        DealerHand dealerHand = new DealerHand(card1);

        int expected = 3;
        int actual = dealerHand.getTotal();

        assertEquals(expected, actual);
    }

    @Test
    void givenDealerHandBustWithAce_whenGetTotal_returnBustValue() {
        Card card1 = new Card(Rank.ACE, Suit.CLUBS);
        DealerHand dealerHand = new DealerHand(card1);
        Card card2 = new Card(Rank.FIVE, Suit.HEARTS);
        Card card3 = new Card(Rank.TEN, Suit.HEARTS);
        Card card4 = new Card(Rank.TEN, Suit.SPADES);
        dealerHand.addCard(card2);
        dealerHand.addCard(card3);
        dealerHand.addCard(card4);

        int expected = 26;
        int actual = dealerHand.getTotal();

        assertEquals(expected, actual);
    }

    @Test
    void givenDealerHandBlackJack_whenGetTotal_returnTwentyOne() {
        Card card1 = new Card(Rank.ACE, Suit.CLUBS);
        DealerHand dealerHand = new DealerHand(card1);
        Card card2 = new Card(Rank.TEN, Suit.HEARTS);
        dealerHand.addCard(card2);

        int expected = 21;
        int actual = dealerHand.getTotal();

        assertEquals(expected, actual);
    }
}