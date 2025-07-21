package de.htwberlin.casino.blackjack.application.domain.model.hands;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Rank;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Suit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerHandTest {

    @Test
    void givenTwoCards_whenCreatePlayerHand_returnNotNull() {
        Card card1 = new Card(Rank.ACE, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.CLUBS);

        PlayerHand playerHand = new PlayerHand(card1, card2);

        assertNotNull(playerHand);
    }

    @Test
    void givenOneCardNull_whenCreatePlayerHand_throwsNullPointerException() {
        Card card1 = new Card(Rank.ACE, Suit.CLUBS);
        assertThrows(NullPointerException.class, () -> new PlayerHand(card1, null));
    }

    @Test
    void givenPlayerHandAndNullCard_whenAddCard_returnFalse() {
        Card card1 = new Card(Rank.ACE, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.CLUBS);
        PlayerHand playerHand = new PlayerHand(card1, card2);

        boolean actual = playerHand.addCard(null);

        assertFalse(actual);
    }

    @Test
    void givenPlayerHandAndActualCard_whenAddCard_returnTrue() {
        Card card1 = new Card(Rank.ACE, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.CLUBS);
        PlayerHand playerHand = new PlayerHand(card1, card2);
        Card card3 = new Card(Rank.THREE, Suit.HEARTS);

        boolean actual = playerHand.addCard(card3);

        assertTrue(actual);
    }

    @Test
    void givenPlayerHandFour_whenAddCardTwo_returnTotalSix() {
        Card card1 = new Card(Rank.TWO, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.SPADES);
        PlayerHand playerHand = new PlayerHand(card1, card2);
        Card card3 = new Card(Rank.TWO, Suit.HEARTS);

        playerHand.addCard(card3);

        int expected = 6;
        int actual = playerHand.getTotal();
        assertEquals(expected, actual);
    }

    @Test
    void givenPlayerHandTwoAces_whenAddCardAce_returnTotalThirteen() {
        Card card1 = new Card(Rank.ACE, Suit.CLUBS);
        Card card2 = new Card(Rank.ACE, Suit.HEARTS);
        PlayerHand playerHand = new PlayerHand(card1, card2);
        Card card3 = new Card(Rank.ACE, Suit.SPADES);

        playerHand.addCard(card3);

        int expected = 13;
        int actual = playerHand.getTotal();
        assertEquals(expected, actual);
    }

    @Test
    void givenTwoCards_whenGetCards_returnListOfSizeTwo() {
        Card card1 = new Card(Rank.ACE, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.HEARTS);
        PlayerHand playerHand = new PlayerHand(card1, card2);

        List<Card> cards = playerHand.getCards();
        int expected = 2;
        int actual = cards.size();

        assertEquals(expected, actual);
    }

    @Test
    void givenTwoCards_whenGetCards_returnCorrectCardInList() {
        Card expected = new Card(Rank.ACE, Suit.CLUBS);
        Card card2 = new Card(Rank.FOUR, Suit.DIAMONDS);
        PlayerHand playerHand = new PlayerHand(expected, card2);

        List<Card> cards = playerHand.getCards();
        Card actual = cards.getFirst();

        assertEquals(expected, actual);
    }

    @Test
    void givenPlayerHandWithTotalFour_whenGetTotal_returnCorrectValue() {
        Card card1 = new Card(Rank.TWO, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.HEARTS);
        PlayerHand playerHand = new PlayerHand(card1, card2);

        int expected = 4;
        int actual = playerHand.getTotal();

        assertEquals(expected, actual);
    }

    @Test
    void givenPlayerHandBustWithAce_whenGetTotal_returnBustValue() {
        Card card1 = new Card(Rank.ACE, Suit.CLUBS);
        Card card2 = new Card(Rank.FIVE, Suit.HEARTS);
        PlayerHand playerHand = new PlayerHand(card1, card2);
        Card card3 = new Card(Rank.TEN, Suit.HEARTS);
        Card card4 = new Card(Rank.TEN, Suit.SPADES);
        playerHand.addCard(card3);
        playerHand.addCard(card4);

        int expected = 26;
        int actual = playerHand.getTotal();

        assertEquals(expected, actual);
    }

    @Test
    void givenPlayerHandBlackJack_whenGetTotal_returnTwentyOne() {
        Card card1 = new Card(Rank.ACE, Suit.CLUBS);
        Card card2 = new Card(Rank.TEN, Suit.HEARTS);
        PlayerHand playerHand = new PlayerHand(card1, card2);

        int expected = 21;
        int actual = playerHand.getTotal();

        assertEquals(expected, actual);
    }
}