package de.htwberlin.casino.blackjack.application.domain.model.cards;

import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.shuffle;

/**
 * Implementation of the card deck used as a basis during a game of blackjack.
 */
public class CardDeckImpl implements CardDeck {

    private final List<Card> deck;

    /**
     * Constructor to create CardDeck from scratch for new game.
     */
    public CardDeckImpl() {
        deck = initializeDeck();
        shuffle(deck);
    }

    /**
     * Constructor to create CardDeck with respect to drawn cards for ongoing game.
     *
     * @param drawnCards cards drawn in earlier turns as part of dealer or player hands
     */
    public CardDeckImpl(List<Card> drawnCards) {
        deck = initializeDeck();
        removeDealtCards(drawnCards);
        shuffle(deck);
    }

    private List<Card> initializeDeck() {
        List<Card> deck = new LinkedList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                deck.add(new Card(rank, suit));
            }
        }
        return deck;
    }

    @Override
    public Card drawCard() {
        return deck.removeFirst();
    }

    @Override
    public boolean removeDealtCards(List<Card> dealtCards) {
        return deck.removeAll(dealtCards);
    }

}
