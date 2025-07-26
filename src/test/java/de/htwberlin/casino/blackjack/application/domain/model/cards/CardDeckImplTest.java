package de.htwberlin.casino.blackjack.application.domain.model.cards;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardDeckImplTest {

    @Test
    public void givenNoCards_whenCreatingDeck_thenInstanceNotNull() {
        CardDeck deck = new CardDeckImpl();

        assertNotNull(deck);
    }

    @Test
    public void givenOneCard_whenCreatingDeck_thenInstanceNotNull() {
        Card card = new Card(Rank.ACE, Suit.SPADES);

        CardDeck actual = new CardDeckImpl(List.of(card));

        assertNotNull(actual);
    }

    @Test
    public void givenThreeCards_whenCreatingDeck_thenInstanceNotNull() {
        Card card1 = new Card(Rank.ACE, Suit.SPADES);
        Card card2 = new Card(Rank.ACE, Suit.CLUBS);
        Card card3 = new Card(Rank.ACE, Suit.HEARTS);

        CardDeck actual = new CardDeckImpl(List.of(card1, card2, card3));

        assertNotNull(actual);
    }

    @Test
    public void givenCardDeck_whenDrawCard_thenInstanceNotNull() {
        CardDeck deck = new CardDeckImpl();

        Card actual = deck.drawCard();

        assertNotNull(actual);
    }

    @Test
    public void givenCardDeck_whenDrawCard_thenInstanceOfCard() {
        CardDeck deck = new CardDeckImpl();

        Card actual = deck.drawCard();

        assertInstanceOf(Card.class, actual);
    }

    @Test
    public void givenCardDeckAndDealtCards_whenRemoveDealtCards_thenReturnTrue (){
        CardDeck deck = new CardDeckImpl();
        Card card1 = new Card(Rank.ACE, Suit.SPADES);
        Card card2 = new Card(Rank.ACE, Suit.CLUBS);
        List<Card> drawnCards = new ArrayList<>();
        drawnCards.add(card1);
        drawnCards.add(card2);

        boolean actual = deck.removeDealtCards(drawnCards);

        assertTrue(actual);
    }

    @Test
    public void givenCardDeckAndDealtCards_whenTwiceRemoveDealtCards_thenReturnFalse (){
        CardDeck deck = new CardDeckImpl();
        Card card1 = new Card(Rank.ACE, Suit.SPADES);
        Card card2 = new Card(Rank.ACE, Suit.CLUBS);
        List<Card> drawnCards = new ArrayList<>();
        drawnCards.add(card1);
        drawnCards.add(card2);

        deck.removeDealtCards(drawnCards);
        boolean actual = deck.removeDealtCards(drawnCards);

        assertFalse(actual);
    }

    @Test
    public void givenCardDeckAndNoDealtCards_whenRemoveDealtCards_thenReturnFalse (){
        CardDeck deck = new CardDeckImpl();
        List<Card> drawnCards = new ArrayList<>();

        boolean actual = deck.removeDealtCards(drawnCards);

        assertFalse(actual);
    }

}