package de.htwberlin.casino.blackjack.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGameRepository extends JpaRepository<GameJpaEntity, Long> {
}
