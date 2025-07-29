package de.htwberlin.casino.blackjack.application.domain.factory.hands;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Rank;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Suit;
import de.htwberlin.casino.blackjack.application.domain.model.hands.DealerHand;
import de.htwberlin.casino.blackjack.application.domain.model.hands.Hand;
import de.htwberlin.casino.blackjack.application.domain.model.hands.HandType;
import de.htwberlin.casino.blackjack.application.domain.model.hands.PlayerHand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HandFactoryImplTest {

    HandFactory factory;

    @BeforeEach
    void setUp() {
        factory = new HandFactoryImpl();
    }

    @Test
    void givenNullHandType_whenCreate_thenThrowsException() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.FIVE, Suit.CLUBS));
        assertThrows(IllegalArgumentException.class, () -> factory.create(null, cards));
    }

    @Test
    void givenNullCards_whenCreate_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> factory.create(HandType.DEALER, null));
    }

    @Test
    void givenDealerEmptyCards_whenCreate_thenThrowsException() {
        List<Card> cards = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> factory.create(HandType.DEALER, cards));
    }

    @Test
    void givenDealerTwoCards_whenCreate_thenReturnDealerHand() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.TWO, Suit.CLUBS));
        cards.add(new Card(Rank.THREE, Suit.CLUBS));

        Hand actual = factory.create(HandType.DEALER, cards);

        assertNotNull(actual);
        assertInstanceOf(DealerHand.class, actual);
    }
    @Test
    void givenDealerThreeCards_whenCreate_thenReturnDealerHand() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.TWO, Suit.CLUBS));
        cards.add(new Card(Rank.THREE, Suit.CLUBS));
        cards.add(new Card(Rank.FOUR, Suit.CLUBS));

        Hand actual = factory.create(HandType.DEALER, cards);

        assertNotNull(actual);
        assertInstanceOf(DealerHand.class, actual);
    }


    @Test
    void givenPlayerOneCard_whenCreate_thenThrowsException() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.TWO, Suit.CLUBS));

        assertThrows(IllegalArgumentException.class, () -> factory.create(HandType.PLAYER, cards));
    }

    @Test
    void givenPlayerTwoCard_whenCreate_thenReturnPlayerHand() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.TWO, Suit.CLUBS));
        cards.add(new Card(Rank.THREE, Suit.CLUBS));

        Hand actual = factory.create(HandType.PLAYER, cards);

        assertNotNull(actual);
        assertInstanceOf(PlayerHand.class, actual);
    }

    @Test
    void givenPlayerThreeCard_whenCreate_thenReturnPlayerHand() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.TWO, Suit.CLUBS));
        cards.add(new Card(Rank.THREE, Suit.CLUBS));
        cards.add(new Card(Rank.FOUR, Suit.CLUBS));

        Hand actual = factory.create(HandType.PLAYER, cards);

        assertNotNull(actual);
        assertInstanceOf(PlayerHand.class, actual);
        assertEquals(3, actual.getCards().size());
    }

}