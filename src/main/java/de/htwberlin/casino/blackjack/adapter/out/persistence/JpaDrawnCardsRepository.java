package de.htwberlin.casino.blackjack.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link DrawnCardJpaEntity} persistence.
 */
public interface JpaDrawnCardsRepository extends JpaRepository<DrawnCardJpaEntity, Long> {
}
