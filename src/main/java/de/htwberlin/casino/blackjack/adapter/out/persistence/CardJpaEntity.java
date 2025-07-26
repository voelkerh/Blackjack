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
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CardJpaEntity {
    /**
     * Unique identifier for the card.
     */
    @Id
    @SequenceGenerator(
            name = "card_sequence",
            sequenceName = "card_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "card_sequence"
    )
    @Column(name = "id")
    private Long id;

    /**
     * Suit of the card (e.g., Hearts, Spades, Clubs, Diamonds).
     */
    @Column(name = "suit", nullable = false)
    private String suit;

    /**
     * Rank of the card (e.g., 2–10, Jack, Queen, King, Ace).
     */
    @Column(name = "rank", nullable = false)
    private String rank;
}
