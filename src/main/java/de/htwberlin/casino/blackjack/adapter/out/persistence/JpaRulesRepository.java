package de.htwberlin.casino.blackjack.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link RulesJpaEntity} persistence.
 */
public interface JpaRulesRepository extends JpaRepository<RulesJpaEntity, String> {
}
