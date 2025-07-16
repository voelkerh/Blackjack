package de.htwberlin.casino.blackjack.application.domain.model.cards;

import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.shuffle;

public class CardDeckImpl implements CardDeck {

    private final List<Card> deck;

    public CardDeckImpl() {
        deck = initializeDeck();
        shuffle(deck);
    }

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
