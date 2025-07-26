package de.htwberlin.casino.blackjack.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link GameJpaEntity} persistence.
 */
public interface JpaGameRepository extends JpaRepository<GameJpaEntity, Long> {
}
