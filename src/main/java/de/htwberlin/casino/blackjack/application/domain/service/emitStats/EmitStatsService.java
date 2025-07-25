package de.htwberlin.casino.blackjack.application.domain.service.emitStats;

import de.htwberlin.casino.blackjack.application.port.in.emitStats.EmitStatsQuery;
import de.htwberlin.casino.blackjack.application.port.in.emitStats.EmitStatsUseCase;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmitStatsService implements EmitStatsUseCase {

    private final StatsProvider statsProvider;

    @Override
    public Result<String, ErrorWrapper> emitStats(EmitStatsQuery query) {
        return null;
    }

    public Result<UserStats, ErrorWrapper> emitUserStats(String userId) {
        if (userId == null || userId.isBlank()) {
            return Result.failure(ErrorWrapper.USER_NOT_FOUND);
        }

        try {
            UserStats stats = statsProvider.fetchUserStats(userId);
            return Result.success(stats);
        } catch (Exception ex) {
            return Result.failure(ErrorWrapper.DATABASE_ERROR);
        }
    }

    public Result<OverviewStats, ErrorWrapper> emitOverviewStats() {
        try {
            OverviewStats stats = statsProvider.fetchOverviewStats();
            return Result.success(stats);
        } catch (Exception ex) {
            return Result.failure(ErrorWrapper.DATABASE_ERROR);
        }
    }
}
