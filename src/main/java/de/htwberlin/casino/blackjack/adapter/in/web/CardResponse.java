package de.htwberlin.casino.blackjack.adapter.in.web;

/**
 *  Record defining output format for cards.
 * @param rank Rank of Card
 * @param suit Suit of Card
 */
public record CardResponse(
        String rank,
        String suit
) {
}
