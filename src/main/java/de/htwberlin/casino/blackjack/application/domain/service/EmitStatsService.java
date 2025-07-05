package de.htwberlin.casino.blackjack.application.domain.service;

import de.htwberlin.casino.blackjack.application.port.in.emitStats.EmitStatsQuery;
import de.htwberlin.casino.blackjack.application.port.in.emitStats.EmitStatsUseCase;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import org.springframework.stereotype.Service;

@Service
class EmitStatsService implements EmitStatsUseCase {
    @Override
    public Result<String, ErrorWrapper> emitStats(EmitStatsQuery query) {
        return null;
    }
}
