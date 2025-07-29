package de.htwberlin.casino.blackjack.adapter.in.web;

/**
 * Record defining output format for overview statistics in chances controller.
 *
 * @param totalGames
 * @param totalPlayers
 * @param totalBet
 * @param houseProfit
 */
public record OverviewStatsResponse(Long totalGames, Long totalPlayers, double totalBet, double houseProfit){
}
