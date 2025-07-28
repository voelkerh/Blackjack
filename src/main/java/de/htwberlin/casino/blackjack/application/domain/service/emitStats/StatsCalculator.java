package de.htwberlin.casino.blackjack.application.domain.service.emitStats;

/**
 * Interface defining methods to calculate various statistical metrics
 * for the blackjack service.
 */
public interface StatsCalculator {
    /**
     * Calculates aggregated statistical data for the entire blackjack service across all users.
     * <p>
     * @param totalGames    total number of games played by all users
     * @param totalPlayers  total number of unique players in the system
     * @param totalBet      total amount of money bet by all users (sum of all bets)
     * @param houseProfit   total amount of money earned by the house from users bets
     * @return an {@link OverviewStats} object containing overall game statistics
     */
    OverviewStats calculateOverviewStats(Long totalGames, Long totalPlayers, double totalBet, double houseProfit);

    /**
     * Calculates statistical data for a specific user based on their game history.
     * <p>
     * Computes the win ratio, net result, and aggregates betting information for the user.
     *
     * @param gamesPlayed  total number of games played by the user
     * @param gamesWon     number of games the user won
     * @param gamesLost    number of games the user lost
     * @param gamesPushed  number of games that ended in a push (tie)
     * @param totalBet     total amount of money the user has bet
     * @param totalWinnings total amount of money the user has won
     * @return a {@link UserStats} object containing the user's game statistics and financial results
     */
    UserStats calculateUserStats(Long gamesPlayed, Long gamesWon, Long gamesLost, Long gamesPushed, double totalBet, double totalWinnings);
}
