package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.model.game.GameState;
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

    @Query("SELECT COUNT(g) FROM game g")
    Long fetchTotalGames();

    @Query("SELECT COUNT(DISTINCT g.userId) FROM game g")
    Long fetchTotalPlayers();

    @Query("SELECT COALESCE(SUM(g.bet), 0) FROM game g")
    Double fetchTotalBet();

    @Query(value = """
            SELECT COALESCE(SUM(
                CASE
                    WHEN game_state = 'WON' THEN -bet
                    WHEN game_state = 'BLACKJACK' THEN -bet * 1.5
                    WHEN game_state = 'LOST' THEN bet
                    ELSE 0
                END
            ), 0)
            FROM game
            """, nativeQuery = true)
    Double fetchHouseProfit();

    @Query("SELECT COUNT(g) FROM game g WHERE g.userId = :userId")
    Long fetchNumberOfGamesPlayedByUser(@Param("userId") String userId);

    @Query("SELECT COUNT(g) FROM game g WHERE g.userId = :userId AND g.gameState = :gameState")
    Long fetchNumberOfGamesWithGameStateOfUser(@Param("userId") String userId, @Param("gameState") GameState gameState);

    @Query("SELECT COALESCE(SUM(g.bet), 0) FROM game g WHERE g.userId = :userId")
    Double fetchTotalBetByUser(@Param("userId") String userId);

    @Query(value = """
            SELECT COALESCE(SUM(
                CASE
                    WHEN game_state = 'WON' THEN bet * 2
                    WHEN game_state = 'BLACKJACK' THEN bet * 2.5
                    WHEN game_state = 'PUSH' THEN bet
                    ELSE 0
                END
            ), 0)
            FROM game
            WHERE user_id = :userId
            """, nativeQuery = true)
    Double fetchNetResultByUser(@Param("userId") String userId);
}
