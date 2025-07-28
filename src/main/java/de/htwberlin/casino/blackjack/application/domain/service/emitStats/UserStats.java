package de.htwberlin.casino.blackjack.application.domain.service.emitStats;

/**
 * Record class holding stats from user of the blackjack game service.
 *
 * @param gamesPlayed
 * @param winRatio
 * @param totalBet
 * @param netResult
 */
public record UserStats(Long gamesPlayed,
                        String winRatio, // Format: "won:lost:push"
                        double totalBet,
                        double netResult) implements Stats {
}