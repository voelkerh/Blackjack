package de.htwberlin.casino.blackjack.application.port.out;

import de.htwberlin.casino.blackjack.application.domain.service.emitStats.OverviewStats;
import de.htwberlin.casino.blackjack.application.domain.service.emitStats.UserStats;

/**
 * Outbound port defining the methods to retrieve blackjack-related statistics from persistent data source.
 * Implemented from an outbound adapter with database access.
 */
public interface LoadStatsPort {
    /**
     * Retrieves statistical data of a specific user
     *
     * @param userId the ID of the user for whom to retrieve statistics
     * @return the requested {@link UserStats} data
     */
    UserStats retrieveUserStats(String userId);

    /**
     * Retrieves statistical data of blackjack service across all users
     *
     * @return the requested {@link OverviewStats} data
     */
    OverviewStats retrieveOverviewStats();
}
