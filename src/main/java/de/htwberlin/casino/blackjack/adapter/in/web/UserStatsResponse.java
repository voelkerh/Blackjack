package de.htwberlin.casino.blackjack.adapter.in.web;

/**
 * Record defining output format for user specific statistics in chances controller.
 *
 * @param gamesPlayed
 * @param winRatio
 * @param totalBet
 * @param netResult
 */
public record UserStatsResponse(int gamesPlayed, String winRatio, int totalBet, int netResult) {
}

