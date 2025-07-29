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
    /**
     * Retrieves a game by its ID and eagerly fetches the associated drawn cards.
     *
     * @param id the unique identifier of the game
     * @return an {@link Optional} containing the {@link GameJpaEntity} with drawn cards if found,
     *         or empty if no game exists with the given ID
     */
    @Query("SELECT g FROM game g LEFT JOIN FETCH g.drawnCards WHERE g.id = :id")
    Optional<GameJpaEntity> findByIdWithDrawnCards(@Param("id") Long id);

    /**
     * Counts the total number of completed games (excluding games currently in progress).
     *
     * @return the count of completed games
     */
    @Query("SELECT COUNT(g) FROM game g WHERE g.gameState <> 'PLAYING'")
    Long fetchTotalGames();

    /**
     * Counts the total number of distinct users who have played at least one completed game.
     *
     * @return the count of distinct players with completed games
     */
    @Query("SELECT COUNT(DISTINCT g.userId) FROM game g WHERE g.gameState <> 'PLAYING'")
    Long fetchTotalPlayers();

    /**
     * Calculates the total sum of bets placed in completed games.
     *
     * @return the sum of all bets for completed games; returns 0 if none found
     */
    @Query("SELECT COALESCE(SUM(g.bet), 0) FROM game g WHERE g.gameState <> 'PLAYING'")
    Double fetchTotalBet();

    /**
     * Calculates the house profit based on completed games.
     * The calculation considers different payouts:
     * <ul>
     *   <li>WON: house loses bet amount (negative profit)</li>
     *   <li>BLACKJACK: house loses 1.5 times bet amount</li>
     *   <li>LOST: house gains the bet amount</li>
     * </ul>
     *
     * @return the net profit for the house; positive means house profit, negative means loss
     */
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

    /**
     * Counts the total number of completed games played by a specific user.
     *
     * @param userId the identifier of the user
     * @return the count of completed games played by the user
     */
    @Query("SELECT COUNT(g) FROM game g WHERE g.userId = :userId AND g.gameState <> 'PLAYING'")
    Long fetchNumberOfGamesPlayedByUser(@Param("userId") String userId);

    /**
     * Counts the number of games with a specific state played by a user.
     * Note: This includes all games with the given state regardless of whether they are completed.
     *
     * @param userId the identifier of the user
     * @param gameState the specific game state to count (e.g., WON, LOST, PUSH)
     * @return the count of games with the given state played by the user
     */
    @Query("SELECT COUNT(g) FROM game g WHERE g.userId = :userId AND g.gameState = :gameState")
    Long fetchNumberOfGamesWithGameStateOfUser(@Param("userId") String userId, @Param("gameState") GameState gameState);

    /**
     * Calculates the total sum of bets placed by a specific user in completed games.
     *
     * @param userId the identifier of the user
     * @return the sum of bets for the user's completed games; 0 if none found
     */
    @Query("SELECT COALESCE(SUM(g.bet), 0) FROM game g WHERE g.userId = :userId AND g.gameState <> 'PLAYING'")
    Double fetchTotalBetByUser(@Param("userId") String userId);

    /**
     * Calculates the total winnings paid to a user for completed games.
     * Winning calculations based on game states:
     * <ul>
     *   <li>WON: 2x the bet</li>
     *   <li>BLACKJACK: 2.5x the bet</li>
     *   <li>PUSH: the original bet amount (no win/loss)</li>
     * </ul>
     *
     * @param userId the identifier of the user
     * @return the total winnings amount for the user; 0 if none found
     */
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
