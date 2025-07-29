package de.htwberlin.casino.blackjack.adapter.in.web;

import de.htwberlin.casino.blackjack.application.domain.service.emitStats.OverviewStats;
import de.htwberlin.casino.blackjack.application.domain.service.emitStats.UserStats;

/**
 * Utility class for mapping domain stats objects to their corresponding response DTOs.
 * <p>
 * Provides static methods to convert {@link UserStats} and {@link OverviewStats} domain objects
 * into their respective response representations {@link UserStatsResponse} and {@link OverviewStatsResponse}.
 * </p>
 */
public class StatsResponseMapper {

    /**
     * Maps a domain object to a {@link UserStatsResponse}.
     *
     * @param resultData the domain object expected to be of type {@link UserStats}
     * @return a {@link UserStatsResponse} representing the user stats
     * @throws IllegalArgumentException if the provided object is not of type {@link UserStats}
     */
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

    /**
     * Maps a domain object to an {@link OverviewStatsResponse}.
     *
     * @param resultData the domain object expected to be of type {@link OverviewStats}
     * @return an {@link OverviewStatsResponse} representing the overview stats
     * @throws IllegalArgumentException if the provided object is not of type {@link OverviewStats}
     */
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

