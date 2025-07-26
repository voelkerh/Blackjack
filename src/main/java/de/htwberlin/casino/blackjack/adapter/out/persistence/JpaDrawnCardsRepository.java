package de.htwberlin.casino.blackjack.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing {@link DrawnCardJpaEntity} persistence.
 */
public interface JpaDrawnCardsRepository extends JpaRepository<DrawnCardJpaEntity, DrawnCardId> {
    /**
     * Finds all drawn cards associated with a specific game.
     *
     * @param game the {@link GameJpaEntity} whose drawn cards to fetch
     * @return list of {@link DrawnCardJpaEntity} for that game
     */
    List<DrawnCardJpaEntity> findByGameId(GameJpaEntity game);
}
