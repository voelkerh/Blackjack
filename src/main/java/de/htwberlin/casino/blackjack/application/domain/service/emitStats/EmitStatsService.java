package de.htwberlin.casino.blackjack.application.domain.service.emitStats;

import de.htwberlin.casino.blackjack.application.domain.model.stats.StatsOption;
import de.htwberlin.casino.blackjack.application.port.in.emitStats.EmitStatsQuery;
import de.htwberlin.casino.blackjack.application.port.in.emitStats.EmitStatsUseCase;
import de.htwberlin.casino.blackjack.application.port.out.LoadStatsPort;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmitStatsService implements EmitStatsUseCase {

    private final LoadStatsPort loadStatsPort;

    @Override
    public  Result<? extends Stats, ErrorWrapper> emitStats(EmitStatsQuery query) {
        StatsOption option = query.option();
        switch (option) {
            case USER -> {
                return emitUserStats(query.userId());
            }
            case OVERVIEW -> {
                return emitOverviewStats();
            }
        }
        return null;
    }

    private Result<UserStats, ErrorWrapper> emitUserStats(String userId) {
        if (userId == null || userId.isBlank()) {
            return Result.failure(ErrorWrapper.USER_NOT_FOUND);
        }
        try {
            UserStats stats = (UserStats) loadStatsPort.retrieveStats(StatsOption.USER, userId);
            return Result.success(stats);
        } catch (Exception ex) {
            return Result.failure(ErrorWrapper.DATABASE_ERROR);
        }
    }

    private Result<OverviewStats, ErrorWrapper> emitOverviewStats() {
        try {
            OverviewStats stats = (OverviewStats) loadStatsPort.retrieveStats(StatsOption.OVERVIEW, null);
            return Result.success(stats);
        } catch (Exception ex) {
            return Result.failure(ErrorWrapper.DATABASE_ERROR);
        }
    }
}
