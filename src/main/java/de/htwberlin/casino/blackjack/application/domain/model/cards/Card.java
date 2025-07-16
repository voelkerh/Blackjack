package de.htwberlin.casino.blackjack.application.domain.model.cards;

/**
 * Card record bundling rank and suit identifying a card from the deck.
 *
 * @param rank
 * @param suit
 */
public record Card(Rank rank, Suit suit) {

}
