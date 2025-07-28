package de.htwberlin.casino.blackjack.application.domain.service.emitStats;

/**
 * Record class holding overall stats from the blackjack game service across all users.
 *
 * @param totalGames
 * @param totalPlayers
 * @param totalBet
 * @param houseProfit
 */
public record OverviewStats(Long totalGames,
                            Long totalPlayers,
                            double totalBet,
                            double houseProfit) implements Stats {
}
