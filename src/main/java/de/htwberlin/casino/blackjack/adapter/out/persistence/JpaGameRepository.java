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

    // Only count completed games (exclude PLAYING)
    @Query("SELECT COUNT(g) FROM game g WHERE g.gameState <> 'PLAYING'")
    Long fetchTotalGames();

    // Count unique users who have at least one completed game
    @Query("SELECT COUNT(DISTINCT g.userId) FROM game g WHERE g.gameState <> 'PLAYING'")
    Long fetchTotalPlayers();

    // Sum bets only for completed games
    @Query("SELECT COALESCE(SUM(g.bet), 0) FROM game g WHERE g.gameState <> 'PLAYING'")
    Double fetchTotalBet();

    // House profit calculated only on completed games
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
            WHERE game_state <> 'PLAYING'
            """, nativeQuery = true)
    Double fetchHouseProfit();

    // Count completed games for specific user
    @Query("SELECT COUNT(g) FROM game g WHERE g.userId = :userId AND g.gameState <> 'PLAYING'")
    Long fetchNumberOfGamesPlayedByUser(@Param("userId") String userId);

    // Count games with specific state for user - no exclusion because query targets one state
    @Query("SELECT COUNT(g) FROM game g WHERE g.userId = :userId AND g.gameState = :gameState")
    Long fetchNumberOfGamesWithGameStateOfUser(@Param("userId") String userId, @Param("gameState") GameState gameState);

    // Sum bets only for completed games of user
    @Query("SELECT COALESCE(SUM(g.bet), 0) FROM game g WHERE g.userId = :userId AND g.gameState <> 'PLAYING'")
    Double fetchTotalBetByUser(@Param("userId") String userId);

    // Sum winnings only for completed games of user
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
            WHERE user_id = :userId AND game_state <> 'PLAYING'
            """, nativeQuery = true)
    Double fetchWinningsByUser(@Param("userId") String userId);
}
