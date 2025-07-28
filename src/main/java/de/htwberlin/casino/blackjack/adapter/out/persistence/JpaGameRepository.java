package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.model.game.GameState;
import de.htwberlin.casino.blackjack.application.domain.service.emitStats.OverviewStats;
import de.htwberlin.casino.blackjack.application.domain.service.emitStats.UserStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repository interface for managing {@link GameJpaEntity} persistence.
 */
public interface JpaGameRepository extends JpaRepository<GameJpaEntity, Long> {

    @Query("SELECT g FROM game g LEFT JOIN FETCH g.drawnCards WHERE g.id = :id")
    Optional<GameJpaEntity> findByIdWithDrawnCards(@Param("id") Long id);

    @Query("SELECT COUNT(g) FROM game g WHERE g.userId = :userId")
    Long fetchNumberOfGamesPlayedByUser(@Param("userId") String userId);

    @Query("SELECT SUM(CASE WHEN g.gameState = :gameState THEN 1 ELSE 0 END) FROM game g WHERE g.userId = :userId")
    int fetchNumberOfGamesWithGameSateOfUser(@Param("userId") String userId, @Param("gameSate") GameState gameState);

    @Query("SELECT COALESCE(SUM(g.bet), 0) FROM game g WHERE g.userId = :userId")
    int fetchTotalBetByUser(@Param("userId") String userId);

    @Query("""
                SELECT new de.htwberlin.casino.blackjack.application.domain.service.emitStats.UserStats(
                    COUNT(g),
                            CONCAT(
                                SUM(CASE WHEN g.gameState = 'WON' THEN 1 ELSE 0 END), ':',
                                SUM(CASE WHEN g.gameState = 'LOST' THEN 1 ELSE 0 END), ':',
                                SUM(CASE WHEN g.gameState = 'PUSH' THEN 1 ELSE 0 END)
                            ),
                            COALESCE(SUM(g.bet), 0),
                            COALESCE(SUM(CASE WHEN g.gameState = 'WON' THEN g.bet ELSE 0 END), 0) -
                            COALESCE(SUM(CASE WHEN g.gameState = 'LOST' THEN g.bet ELSE 0 END), 0)
                        )
                FROM game g
                WHERE g.userId = :userId
            """)
    UserStats fetchUserStats(@Param("userId") String userId);

    @Query("""
                SELECT new de.htwberlin.casino.blackjack.application.domain.service.emitStats.OverviewStats(
                    COUNT(g),
                    COUNT(DISTINCT g.userId),
                    COALESCE(SUM(g.bet), 0),
                    COALESCE(SUM(CASE WHEN g.gameState = 'LOST' THEN g.bet ELSE 0 END), 0) -
                    COALESCE(SUM(CASE WHEN g.gameState = 'WON' THEN g.bet ELSE 0 END), 0)
                )
                FROM game g
            """)
    OverviewStats fetchOverviewStats();
}
