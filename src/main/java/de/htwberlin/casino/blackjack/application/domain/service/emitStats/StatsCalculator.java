package de.htwberlin.casino.blackjack.application.domain.service.emitStats;

public interface StatsCalculator {
    /**
     * Calculates statistical data of a specific user
     *
     * @param userId the ID of the user for whom to retrieve statistics
     * @return the requested {@link UserStats} data
     */
    UserStats calculateUserStats(String userId);

    /**
     * Calculates statistical data of blackjack service across all users
     *
     * @return the requested {@link OverviewStats} data
     */
    OverviewStats calculateOverviewStats();
}
