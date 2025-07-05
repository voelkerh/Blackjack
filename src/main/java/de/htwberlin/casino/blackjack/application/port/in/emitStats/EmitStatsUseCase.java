package de.htwberlin.casino.blackjack.application.port.in.emitStats;

import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;

public interface EmitStatsUseCase {
    Result<String, ErrorWrapper> emitStats(EmitStatsQuery query);
}
