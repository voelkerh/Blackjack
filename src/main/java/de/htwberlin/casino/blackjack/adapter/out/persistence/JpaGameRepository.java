package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.service.emitStats.OverviewStats;
import de.htwberlin.casino.blackjack.application.domain.service.emitStats.UserStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for managing {@link GameJpaEntity} persistence.
 */
public interface JpaGameRepository extends JpaRepository<GameJpaEntity, Long> {

    @Query("""
                SELECT new de.htwberlin.casino.blackjack.application.domain.service.emitStats.UserStats(
                    COUNT(g),
                            CONCAT(
                                SUM(CASE WHEN g.gameState = 'WIN' THEN 1 ELSE 0 END), ':',
                                SUM(CASE WHEN g.gameState = 'LOSS' THEN 1 ELSE 0 END), ':',
                                SUM(CASE WHEN g.gameState = 'TIE' THEN 1 ELSE 0 END)
                            ),
                            COALESCE(SUM(g.bet), 0),
                            COALESCE(SUM(CASE WHEN g.gameState = 'WIN' THEN g.bet ELSE 0 END), 0) -
                            COALESCE(SUM(CASE WHEN g.gameState = 'LOSS' THEN g.bet ELSE 0 END), 0)
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
        COALESCE(SUM(CASE WHEN g.gameState = 'LOSS' THEN g.bet ELSE 0 END), 0) -
        COALESCE(SUM(CASE WHEN g.gameState = 'WIN' THEN g.bet ELSE 0 END), 0)
    )
    FROM game g
""")
    OverviewStats fetchOverviewStats();
}
