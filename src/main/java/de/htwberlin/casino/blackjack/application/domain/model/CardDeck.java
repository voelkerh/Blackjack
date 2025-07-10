package de.htwberlin.casino.blackjack.application.domain.model;

import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.shuffle;

public class CardDeck {

    private static CardDeck instance;
    private final List<Card> deck;

    private CardDeck() {
        deck = initializeDeck();
        shuffle(deck);
    }

    public static CardDeck getInstance(){
        if(instance == null) instance = new CardDeck();
        return instance;
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

    public Card drawCard() {
        return deck.removeFirst();
    }

    /**
     * Remove all dealt cards included in player and dealer hand from card deck.
     *
     * @param dealtCards list constructed from player and dealer hand in services
     */
    public void removeDealtCards(List<Card> dealtCards) {
    }

}
