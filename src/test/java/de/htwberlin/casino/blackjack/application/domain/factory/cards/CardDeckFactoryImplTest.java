package de.htwberlin.casino.blackjack.application.domain.factory.cards;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.cards.CardDeck;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Rank;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Suit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardDeckFactoryImplTest {

    @Test
    public void givenNoDrawnCards_whenCreatingDeckFromScratch_thenInstanceNotNull() {
        CardDeckFactoryImpl cardDeckFactory = new CardDeckFactoryImpl();

        Object actual = cardDeckFactory.fromScratch();

        assertNotNull(actual);
    }

    @Test
    public void givenNoDrawnCards_whenCreatingDeckFromScratch_thenInstanceofCardDeck() {
        CardDeckFactoryImpl cardDeckFactory = new CardDeckFactoryImpl();

        Object actual = cardDeckFactory.fromScratch();

        assertInstanceOf(CardDeck.class, actual);
    }

    @Test
    public void givenDrawnCards_whenCreatingDeckWithDrawnCards_thenInstanceNotNull() {
        CardDeckFactoryImpl cardDeckFactory = new CardDeckFactoryImpl();
        Card card1 = new Card(Rank.FIVE, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.CLUBS);
        Card card3 = new Card(Rank.THREE, Suit.CLUBS);
        List<Card> drawnCards = List.of(card1, card2, card3);

        CardDeck actual = cardDeckFactory.withDrawnCards(drawnCards);

        assertNotNull(actual);
    }

    @Test
    public void givenDrawnCards_whenCreatingDeckWithDrawnCards_thenInstanceOfCardDeck() {
        CardDeckFactoryImpl cardDeckFactory = new CardDeckFactoryImpl();
        Card card1 = new Card(Rank.FIVE, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.CLUBS);
        Card card3 = new Card(Rank.THREE, Suit.CLUBS);
        List<Card> drawnCards = List.of(card1, card2, card3);

        Object actual = cardDeckFactory.withDrawnCards(drawnCards);

        assertInstanceOf(CardDeck.class, actual);
    }

    @Test
    public void givenEmptyCardList_whenCreatingDeckWithDrawnCards_thenIllegalArgumentException() {
        CardDeckFactoryImpl cardDeckFactory = new CardDeckFactoryImpl();

        assertThrows(IllegalArgumentException.class, () -> cardDeckFactory.withDrawnCards(List.of()));
    }

    @Test
    public void givenNull_whenCreatingDeckWithDrawnCards_thenNullPointerException() {
        CardDeckFactoryImpl cardDeckFactory = new CardDeckFactoryImpl();

        assertThrows(NullPointerException.class, () -> cardDeckFactory.withDrawnCards(null));
    }
}