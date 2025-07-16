package de.htwberlin.casino.blackjack.application.domain.model.cards;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.shuffle;

@Component
public class CardDeckImpl implements CardDeck {

    private static CardDeckImpl instance;
    private final List<Card> deck;

    private CardDeckImpl() {
        deck = initializeDeck();
        shuffle(deck);
    }

    public static CardDeckImpl getInstance(){
        if(instance == null) instance = new CardDeckImpl();
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

    @Override
    public Card drawCard() {
        return deck.removeFirst();
    }

    @Override
    public boolean removeDealtCards(List<Card> dealtCards) {
        return deck.removeAll(dealtCards);
    }

}
