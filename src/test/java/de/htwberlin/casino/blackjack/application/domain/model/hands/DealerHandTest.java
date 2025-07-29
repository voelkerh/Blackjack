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
        Card downCard = new Card(Rank.TWO, Suit.CLUBS);

        DealerHand dealerHand = new DealerHand(upCard, downCard);

        assertNotNull(dealerHand);
    }

    @Test
    void givenUpCardNull_whenCreateDealerHand_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DealerHand(null, null));
    }

    @Test
    void givenDealerHandAndCard_whenGetUpCard_returnCorrectCard() {
        Card expected = new Card(Rank.ACE, Suit.CLUBS);
        Card downCard = new Card(Rank.TWO, Suit.CLUBS);
        DealerHand dealerHand = new DealerHand(expected, downCard);

        Card actual = dealerHand.getUpCard();

        assertEquals(expected, actual);
    }

    @Test
    void givenDealerHandAndNullCard_whenAddCard_returnFalse() {
        Card card1 = new Card(Rank.ACE, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.CLUBS);
        DealerHand dealerHand = new DealerHand(card1, card2);

        boolean actual = dealerHand.addCard(null);

        assertFalse(actual);
    }

    @Test
    void givenDealerHandAndActualCard_whenAddCard_returnTrue() {
        Card card1 = new Card(Rank.ACE, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.CLUBS);
        DealerHand dealerHand = new DealerHand(card1, card2);
        Card card3 = new Card(Rank.THREE, Suit.HEARTS);

        boolean actual = dealerHand.addCard(card3);

        assertTrue(actual);
    }

    @Test
    void givenDealerHandTwoTwos_whenAddCardTwo_returnTotalSix() {
        Card card1 = new Card(Rank.TWO, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.HEARTS);
        DealerHand dealerHand = new DealerHand(card1, card2);
        Card card3 = new Card(Rank.TWO, Suit.SPADES);

        dealerHand.addCard(card3);

        int expected = 6;
        int actual = dealerHand.getTotal();
        assertEquals(expected, actual);
    }

    @Test
    void givenDealerHandTwoAces_whenAddCardTwo_returnTotalFourteen() {
        Card card1 = new Card(Rank.ACE, Suit.CLUBS);
        Card card2 = new Card(Rank.ACE, Suit.HEARTS);
        DealerHand dealerHand = new DealerHand(card1, card2);
        Card card3 = new Card(Rank.TWO, Suit.HEARTS);

        dealerHand.addCard(card3);

        int expected = 14;
        int actual = dealerHand.getTotal();
        assertEquals(expected, actual);
    }

    @Test
    void givenThreeCards_whenGetCards_returnListOfSizeThree() {
        Card card1 = new Card(Rank.ACE, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.HEARTS);
        Card card3 = new Card(Rank.TWO, Suit.CLUBS);
        DealerHand dealerHand = new DealerHand(card1, card2);
        dealerHand.addCard(card3);

        List<Card> cards = dealerHand.getCards();
        int expected = 3;
        int actual = cards.size();

        assertEquals(expected, actual);
    }

    @Test
    void givenTwoCard_whenGetCards_returnCorrectFirstCardInList() {
        Card expected = new Card(Rank.ACE, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.HEARTS);
        DealerHand dealerHand = new DealerHand(expected, card2);

        List<Card> cards = dealerHand.getCards();
        Card actual = cards.getFirst();

        assertEquals(expected, actual);
    }

    @Test
    void givenDealerHandWithTotalFive_whenGetTotal_returnCorrectValue() {
        Card card1 = new Card(Rank.THREE, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.HEARTS);
        DealerHand dealerHand = new DealerHand(card1, card2);

        int expected = 5;
        int actual = dealerHand.getTotal();

        assertEquals(expected, actual);
    }

    @Test
    void givenDealerHandBustWithAce_whenGetTotal_returnBustValue() {
        Card card1 = new Card(Rank.ACE, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.HEARTS);
        DealerHand dealerHand = new DealerHand(card1, card2);
        Card card3 = new Card(Rank.THREE, Suit.HEARTS);
        Card card4 = new Card(Rank.TEN, Suit.HEARTS);
        Card card5 = new Card(Rank.TEN, Suit.SPADES);
        dealerHand.addCard(card3);
        dealerHand.addCard(card4);
        dealerHand.addCard(card5);

        int expected = 26;
        int actual = dealerHand.getTotal();

        assertEquals(expected, actual);
    }

    @Test
    void givenDealerHandBlackJack_whenGetTotal_returnTwentyOne() {
        Card card1 = new Card(Rank.ACE, Suit.CLUBS);
        Card card2 = new Card(Rank.TEN, Suit.HEARTS);
        DealerHand dealerHand = new DealerHand(card1, card2);

        int expected = 21;
        int actual = dealerHand.getTotal();

        assertEquals(expected, actual);
    }
}