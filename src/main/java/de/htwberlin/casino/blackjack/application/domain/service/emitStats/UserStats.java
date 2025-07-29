package de.htwberlin.casino.blackjack.application.domain.service.emitStats;

/**
 * Record class holding stats from user of the blackjack game service.
 *
 * @param gamesPlayed   total number of games played by the user
 * @param winRatio      format: "blackjack:won:lost:push"
 * @param totalBet      total amount of money the user has bet
 * @param netResult     total amount of money the user has won/lost (winnings-bets)
 */
public record UserStats(Long gamesPlayed,
                        String winRatio,
                        double totalBet,
                        double netResult) implements Stats {
}