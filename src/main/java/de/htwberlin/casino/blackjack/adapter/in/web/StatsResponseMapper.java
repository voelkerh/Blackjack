package de.htwberlin.casino.blackjack.adapter.in.web;

import de.htwberlin.casino.blackjack.application.domain.service.emitStats.OverviewStats;
import de.htwberlin.casino.blackjack.application.domain.service.emitStats.UserStats;

public class StatsResponseMapper {

    public static UserStatsResponse toUserStatsResponse(Object resultData) {
        if (resultData instanceof UserStats(Long gamesPlayed, String winRatio, double totalBet, double netResult)) {
            return new UserStatsResponse(
                    gamesPlayed,
                    winRatio,
                    totalBet,
                    netResult
            );
        } else {
            throw new IllegalArgumentException("Expected UserStats but got " + resultData.getClass().getSimpleName());
        }
    }

    public static OverviewStatsResponse toOverviewStatsResponse(Object resultData) {
        if (resultData instanceof OverviewStats(
                Long totalGames, Long totalPlayers, double totalBet, double houseProfit
        )) {
            return new OverviewStatsResponse(
                    totalGames,
                    totalPlayers,
                    totalBet,
                    houseProfit
            );
        } else {
            throw new IllegalArgumentException("Expected OverviewStats but got " + resultData.getClass().getSimpleName());
        }
    }
}

