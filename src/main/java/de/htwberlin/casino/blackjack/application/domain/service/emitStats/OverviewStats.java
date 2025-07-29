package de.htwberlin.casino.blackjack.application.domain.service.emitStats;

/**
 * Record class holding overall stats from the blackjack game service across all users.
 *
 * @param totalGames    total number of games played by all users
 * @param totalPlayers  total number of unique players in the system
 * @param totalBet      total amount of money bet by all users (sum of all bets)
 * @param houseProfit   total amount of money earned by the house from users bets
 */
public record OverviewStats(Long totalGames,
                            Long totalPlayers,
                            double totalBet,
                            double houseProfit) implements Stats {
}
