package de.htwberlin.casino.blackjack.application.domain.service.emitStats;

import de.htwberlin.casino.blackjack.application.domain.model.game.GameState;
import de.htwberlin.casino.blackjack.application.domain.model.stats.StatsOption;
import de.htwberlin.casino.blackjack.application.port.in.emitStats.EmitStatsQuery;
import de.htwberlin.casino.blackjack.application.port.in.emitStats.EmitStatsUseCase;
import de.htwberlin.casino.blackjack.application.port.out.LoadStatsPort;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Application service that handles stats retrieval logic.
 * Implements the {@link EmitStatsUseCase} port.
 */
@Service
@RequiredArgsConstructor
public class EmitStatsService implements EmitStatsUseCase {

    private final StatsCalculator statsCalculator;
    private final LoadStatsPort loadStatsPort;

    @Override
    public Result<? extends Stats, ErrorWrapper> emitStats(EmitStatsQuery query) {
        StatsOption option = query.option();
        switch (option) {
            case USER -> {
                return emitUserStats(query.userId());
            }
            case OVERVIEW -> {
                return emitOverviewStats();
            }
        }
        return Result.failure(ErrorWrapper.INVALID_STATS_OPTION);
    }

    /**
     * Emits statistical data of blackjack service across all users
     *
     * @return the requested {@link OverviewStats} data
     */
    private Result<OverviewStats, ErrorWrapper> emitOverviewStats() {
        try {
            Long totalGames = loadStatsPort.retrieveTotalGames();
            Long totalPlayers = loadStatsPort.retrieveTotalPlayers();
            double totalBet = loadStatsPort.retrieveTotalBet();
            double houseProfit = loadStatsPort.retrieveHouseProfit();

            OverviewStats stats = statsCalculator.calculateOverviewStats(
                    totalGames, totalPlayers, totalBet, houseProfit
            );

            return Result.success(stats);
        } catch (Exception ex) {
            return Result.failure(ErrorWrapper.DATABASE_ERROR);
        }
    }

    /**
     * Emits statistical data of a specific user
     *
     * @param userId the ID of the user for whom to retrieve statistics
     * @return the requested {@link UserStats} data
     */
    private Result<UserStats, ErrorWrapper> emitUserStats(String userId) {
        if (userId == null || userId.isBlank()) {
            return Result.failure(ErrorWrapper.INVALID_USER_ID);
        }

        try {
            Long gamesPlayed = loadStatsPort.retrieveNumberOfGamesPlayedByUser(userId);
            Long gamesBlackjack = loadStatsPort.retrieveNumberOfGamesWithGameSateOfUser(userId, GameState.BLACKJACK);
            Long gamesWon = loadStatsPort.retrieveNumberOfGamesWithGameSateOfUser(userId, GameState.WON);
            Long gamesLost = loadStatsPort.retrieveNumberOfGamesWithGameSateOfUser(userId, GameState.LOST);
            Long gamesPushed = loadStatsPort.retrieveNumberOfGamesWithGameSateOfUser(userId, GameState.PUSH);
            Double totalBet = loadStatsPort.retrieveTotalBetByUser(userId);
            Double winnings = loadStatsPort.retrieveWinningsByUser(userId);

            UserStats stats = statsCalculator.calculateUserStats(
                    gamesPlayed, gamesWon, gamesLost, gamesPushed, gamesBlackjack, totalBet, winnings
            );

            return Result.success(stats);
        } catch (Exception ex) {
            return Result.failure(ErrorWrapper.DATABASE_ERROR);
        }
    }
}
