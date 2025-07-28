package de.htwberlin.casino.blackjack.application.port.out;

import de.htwberlin.casino.blackjack.application.domain.model.game.GameState;

/**
 * Outbound port defining the methods to retrieve blackjack-related statistics from persistent data source.
 * <p>
 * This port is typically implemented by an outbound adapter that interacts
 * with the data layer (e.g., JPA repository) to provide raw statistical data.
 */
public interface LoadStatsPort {

    /**
     * Retrieves the total number of games played by all users.
     *
     * @return the total count of games
     */
    Long retrieveTotalGames();

    /**
     * Retrieves the total number of unique players who have played at least one game.
     *
     * @return the total number of distinct players
     */
    Long retrieveTotalPlayers();

    /**
     * Retrieves the total amount of money bet across all games.
     *
     * @return the total sum of all bets placed
     */
    Double retrieveTotalBet();

    /**
     * Retrieves the total house profit across all games.

     * @return the total house profit
     */
    Double retrieveHouseProfit();

    /**
     * Retrieves the number of games played by a specific user.
     *
     * @param userId the identifier of the user
     * @return the total number of games the user has played
     */
    Long retrieveNumberOfGamesPlayedByUser(String userId);

    /**
     * Retrieves the number of games a user has played with a specific game state (e.g., WIN, LOSS).
     *
     * @param userId    the identifier of the user
     * @param gameState the game outcome to filter by
     * @return the number of games the user played in the given game state
     */
    Long retrieveNumberOfGamesWithGameSateOfUser(String userId, GameState gameState);

    /**
     * Retrieves the total amount of money a specific user has bet.
     *
     * @param userId the identifier of the user
     * @return the total sum of bets placed by the user
     */
    Double retrieveTotalBetByUser(String userId);

    /**
     * Retrieves the net result (winnings - bets) for a specific user.
     * <p>
     * This includes correct payout multipliers (e.g. 3:2 for blackjack).
     *
     * @param userId the identifier of the user
     * @return the net result as a profit (positive) or loss (negative)
     */
    Double retrieveNetResultByUser(String userId);
}
