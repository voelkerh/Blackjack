package de.htwberlin.casino.blackjack.application.port.out;

import de.htwberlin.casino.blackjack.application.domain.model.stats.StatsOption;
import de.htwberlin.casino.blackjack.application.domain.service.emitStats.OverviewStats;
import de.htwberlin.casino.blackjack.application.domain.service.emitStats.Stats;
import de.htwberlin.casino.blackjack.application.domain.service.emitStats.UserStats;

/**
 * Outbound port defining the methods to retrieve blackjack-related statistics from persistent data source.
 * Implemented from an outbound adapter with database access.
 */
public interface LoadStatsPort {
    /**
     * Retrieves statistical data based on the specified stats option.
     *
     * @param option the type of statistics to retrieve (e.g., USER or OVERVIEW)
     * @param userId the user identifier for user-specific statistics; may be null if not applicable
     * @return the statistics data corresponding to the given option (e.g., {@link UserStats} or {@link OverviewStats}
     */
    Stats retrieveStats(StatsOption option, String userId);
}
