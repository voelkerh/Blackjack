package de.htwberlin.casino.blackjack.application.domain.service.emitStats;

/**
 * Defines methods for retrieving blackjack-related statistics.
 * Separates the logic of fetching user-specific and overall game statistics from service.
 */
public interface StatsProvider {
    /**
     * Fetches blackjack statistics for a specific user.
     *
     * @param userId the unique identifier of the user
     * @return a {@link UserStats} object containing the user's blackjack game statistics
     * @throws IllegalArgumentException if the userId is null or invalid
     */
    UserStats fetchUserStats(String userId);

    /**
     * Fetches overall blackjack statistics across all users.
     *
     * @return an {@link OverviewStats} object containing aggregated game statistics
     */
    OverviewStats fetchOverviewStats();
}
