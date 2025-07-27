package de.htwberlin.casino.blackjack.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface for managing {@link DrawnCardJpaEntity} persistence.
 */
public interface JpaDrawnCardsRepository extends JpaRepository<DrawnCardJpaEntity, Long> {
    /**
     * Finds all drawn cards associated with a specific game.
     *
     * @param game the {@link GameJpaEntity} whose drawn cards to fetch
     * @return list of {@link DrawnCardJpaEntity} for that game
     */
    List<DrawnCardJpaEntity> findByGame(@Param("game") GameJpaEntity game);
}
