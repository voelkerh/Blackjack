package de.htwberlin.casino.blackjack.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link CardJpaEntity} persistence.
 */
public interface JpaCardRepository extends JpaRepository<CardJpaEntity, Long> {
    Optional<CardJpaEntity> findBySuitAndRank(String suit, String rank);
}

