package de.htwberlin.casino.blackjack.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * JPA entity representing a card in the card deck.
 * Each card has a suit and a rank (e.g., "Hearts" and "Ace").
 */
@Entity(name = "cards")
@IdClass(CardId.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CardJpaEntity {
    /**
     * Suit of the card (e.g., Hearts, Spades, Clubs, Diamonds).
     */
    @Id
    @Column(name = "suit", nullable = false)
    private String suit;

    /**
     * Rank of the card (e.g., 2–10, Jack, Queen, King, Ace).
     */
    @Id
    @Column(name = "rank", nullable = false)
    private String rank;
}
