package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Rank;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Suit;
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
     * {@link Rank} of the card (e.g., 2–10, Jack, Queen, King, Ace).
     */
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "rank", nullable = false)
    private Rank rank;
    /**
     * {@link Suit} of the card (e.g., Hearts, Spades, Clubs, Diamonds).
     */
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "suit", nullable = false)
    private Suit suit;
}
