package de.htwberlin.casino.blackjack.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRulesRepository extends JpaRepository<RulesJpaEntity, String> {
}
