package de.htwberlin.casino.blackjack.application.port.in.emitStats;

import de.htwberlin.casino.blackjack.application.domain.model.StatOption;

public record EmitStatsQuery(StatOption option, String userId) {

    public EmitStatsQuery(StatOption option) {
        this(option, null); // For overview stats where userId is not needed
    }
}
