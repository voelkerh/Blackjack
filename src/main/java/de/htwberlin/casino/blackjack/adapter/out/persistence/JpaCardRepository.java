package de.htwberlin.casino.blackjack.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link CardJpaEntity} persistence.
 */
public interface JpaCardRepository extends JpaRepository<CardJpaEntity, Long> {
}

