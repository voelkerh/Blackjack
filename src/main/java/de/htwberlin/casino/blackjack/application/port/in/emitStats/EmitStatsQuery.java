package de.htwberlin.casino.blackjack.application.port.in.emitStats;

import de.htwberlin.casino.blackjack.application.domain.model.stats.StatsOption;

/**
 * Query to emit statistics for a specific option and user.
 *
 * @param option the {@link StatsOption} being requested (e.g., user specific or overview).
 * @param userId the ID of the user whose stats are being requested, or {@code null} for general stats.
 */
public record EmitStatsQuery(StatsOption option, String userId) {

    /**
     * Convenience constructor for requesting general statistics (not user-specific).
     *
     * @param option the {@link StatsOption} being requested.
     * {@code userId} is set to null
     */
    public EmitStatsQuery(StatsOption option) {
        this(option, null); // For overview stats where userId is not needed
    }
}
