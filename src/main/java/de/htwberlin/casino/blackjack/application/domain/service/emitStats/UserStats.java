package de.htwberlin.casino.blackjack.application.domain.service.emitStats;

/**
 * Record class holding stats from user of the blackjack game service.
 *
 * @param gamesPlayed
 * @param winRatio
 * @param totalBet
 * @param netResult
 */
public record UserStats(int gamesPlayed,
                        String winRatio, // Format: "win:loss:tie"
                        int totalBet,
                        int netResult) implements Stats {
}