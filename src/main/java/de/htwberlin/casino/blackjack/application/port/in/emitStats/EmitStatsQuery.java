package de.htwberlin.casino.blackjack.application.port.in.emitStats;

import de.htwberlin.casino.blackjack.application.domain.model.stats.StatsOption;

public record EmitStatsQuery(StatsOption option, String userId) {

    public EmitStatsQuery(StatsOption option) {
        this(option, null); // For overview stats where userId is not needed
    }
}
