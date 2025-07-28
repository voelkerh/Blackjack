package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Rank;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Suit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link CardJpaEntity} persistence.
 */
public interface JpaCardRepository extends JpaRepository<CardJpaEntity, Long> {
    /**
     * Finds a card entity by its suit and rank.
     *
     * @param suit the {@link Suit} of the card (e.g., "Hearts", "Spades")
     * @param rank the {@link Rank} of the card (e.g., "Ace", "10", "King")
     * @return an {@link Optional} containing the matching {@link CardJpaEntity} if found,
     * or empty if no card matches the specified suit and rank
     */
    Optional<CardJpaEntity> findBySuitAndRank(Suit suit, Rank rank);
}

