package de.htwberlin.casino.blackjack.application.port.out;

import de.htwberlin.casino.blackjack.application.domain.model.stats.StatsOption;
import de.htwberlin.casino.blackjack.application.domain.service.emitStats.Stats;

/**
 * Outbound port defining the methods to retrieve blackjack-related statistics from persistent data source.
 * Implemented from an outbound adapter with database access.
 */
public interface LoadStatsPort {
    // TODO: javadoc
    Stats retrieveStats(StatsOption option, String userId);
}
