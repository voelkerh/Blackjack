package de.htwberlin.casino.blackjack.application.domain.service.emitStats;

import de.htwberlin.casino.blackjack.application.domain.model.game.GameState;
import de.htwberlin.casino.blackjack.application.domain.model.stats.StatsOption;
import de.htwberlin.casino.blackjack.application.port.in.emitStats.EmitStatsQuery;
import de.htwberlin.casino.blackjack.application.port.out.LoadStatsPort;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class EmitStatsServiceTest {

    private StatsCalculator mockCalculator;
    private LoadStatsPort mockPort;
    private EmitStatsService service;

    @BeforeEach
    void setUp() {
        mockCalculator = Mockito.mock(StatsCalculator.class);
        mockPort = Mockito.mock(LoadStatsPort.class);
        service = new EmitStatsService(mockCalculator, mockPort);
    }

    @Test
    void givenOverviewQuery_whenEmitStats_thenReturnOverviewStats() {
        EmitStatsQuery query = new EmitStatsQuery(StatsOption.OVERVIEW, null);

        Long totalGames = 100L;
        Long totalPlayers = 10L;
        double totalBet = 5000.0;
        double houseProfit = 1500.0;
        OverviewStats expected = new OverviewStats(totalGames, totalPlayers, totalBet, houseProfit);

        when(mockPort.retrieveTotalGames()).thenReturn(totalGames);
        when(mockPort.retrieveTotalPlayers()).thenReturn(totalPlayers);
        when(mockPort.retrieveTotalBet()).thenReturn(totalBet);
        when(mockPort.retrieveHouseProfit()).thenReturn(houseProfit);
        when(mockCalculator.calculateOverviewStats(totalGames, totalPlayers, totalBet, houseProfit))
                .thenReturn(expected);

        Result<? extends Stats, ErrorWrapper> result = service.emitStats(query);
        assertTrue(result.isSuccess());
        assertEquals(expected, result.getSuccessData().get());
    }

    @Test
    void givenUserQuery_whenEmitStats_thenReturnUserStats() {
        String userId = "user123";
        EmitStatsQuery query = new EmitStatsQuery(StatsOption.USER, userId);

        Long gamesPlayed = 30L, won = 10L, lost = 15L, pushed = 3L, blackjack = 2L;
        double totalBet = 2000.0, winnings = 2200.0;
        UserStats expected = new UserStats(gamesPlayed, "2:10:15:3", totalBet, 200.0);

        when(mockPort.retrieveNumberOfGamesPlayedByUser(userId)).thenReturn(gamesPlayed);
        when(mockPort.retrieveNumberOfGamesWithGameSateOfUser(userId, GameState.BLACKJACK)).thenReturn(blackjack);
        when(mockPort.retrieveNumberOfGamesWithGameSateOfUser(userId, GameState.WON)).thenReturn(won);
        when(mockPort.retrieveNumberOfGamesWithGameSateOfUser(userId, GameState.LOST)).thenReturn(lost);
        when(mockPort.retrieveNumberOfGamesWithGameSateOfUser(userId, GameState.PUSH)).thenReturn(pushed);
        when(mockPort.retrieveTotalBetByUser(userId)).thenReturn(totalBet);
        when(mockPort.retrieveWinningsByUser(userId)).thenReturn(winnings);

        when(mockCalculator.calculateUserStats(gamesPlayed, won, lost, pushed, blackjack, totalBet, winnings))
                .thenReturn(expected);

        Result<? extends Stats, ErrorWrapper> result = service.emitStats(query);
        assertTrue(result.isSuccess());
        assertEquals(expected, result.getSuccessData().get());
    }

    @Test
    void givenUserQueryWithInvalidUserId_whenEmitStats_thenReturnError() {
        EmitStatsQuery query = new EmitStatsQuery(StatsOption.USER, " ");

        Result<? extends Stats, ErrorWrapper> result = service.emitStats(query);
        assertTrue(result.isFailure());
        assertEquals(ErrorWrapper.INVALID_USER_ID, result.getFailureData().get());
    }

    @Test
    void givenOverviewQueryWithException_whenEmitStats_thenReturnDatabaseError() {
        EmitStatsQuery query = new EmitStatsQuery(StatsOption.OVERVIEW, null);

        when(mockPort.retrieveTotalGames()).thenThrow(RuntimeException.class);

        Result<? extends Stats, ErrorWrapper> result = service.emitStats(query);
        assertTrue(result.isFailure());
        assertEquals(ErrorWrapper.DATABASE_ERROR, result.getFailureData().get());
    }

    @Test
    void givenUserQueryWithException_whenEmitStats_thenReturnDatabaseError() {
        EmitStatsQuery query = new EmitStatsQuery(StatsOption.USER, "user123");

        when(mockPort.retrieveNumberOfGamesPlayedByUser("user123")).thenThrow(RuntimeException.class);

        Result<? extends Stats, ErrorWrapper> result = service.emitStats(query);
        assertTrue(result.isFailure());
        assertEquals(ErrorWrapper.DATABASE_ERROR, result.getFailureData().get());
    }

    @Test
    void givenInvalidStatsOption_whenEmitStats_thenReturnInvalidStatsOptionError() {
        EmitStatsQuery query = Mockito.mock(EmitStatsQuery.class);
        when(query.option()).thenReturn(null);

        Result<? extends Stats, ErrorWrapper> result = service.emitStats(query);
        assertTrue(result.isFailure());
        assertEquals(ErrorWrapper.INVALID_STATS_OPTION, result.getFailureData().get());
    }
}
