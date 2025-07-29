package de.htwberlin.casino.blackjack.adapter.in.web;

import de.htwberlin.casino.blackjack.application.domain.service.emitStats.OverviewStats;
import de.htwberlin.casino.blackjack.application.domain.service.emitStats.UserStats;
import de.htwberlin.casino.blackjack.application.port.in.emitStats.EmitStatsQuery;
import de.htwberlin.casino.blackjack.application.port.in.emitStats.EmitStatsUseCase;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;


class StatsControllerTest {

    private EmitStatsUseCase emitStatsUseCase;
    private StatsController statsController;

    @BeforeEach
    void setUp() {
        emitStatsUseCase = mock(EmitStatsUseCase.class);
        statsController = new StatsController(emitStatsUseCase);
    }

    @Test
    void givenValidQuery_whenEmitStats_thenReturnUserStats() {
        UserStats userStats = new UserStats(10L, "5:0:0:0", 100.0, 20.0);

        when(emitStatsUseCase.emitStats(any())).thenAnswer(invocation -> Result.success(userStats));

        ResponseEntity<?> response = statsController.readUserStats("user");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Object body = response.getBody();
        assertInstanceOf(UserStatsResponse.class, body);
        UserStatsResponse actual = (UserStatsResponse) body;
        assertEquals(userStats.gamesPlayed(), actual.gamesPlayed());
        assertEquals(userStats.winRatio(), actual.winRatio());
        assertEquals(userStats.totalBet(), actual.totalBet());
        assertEquals(userStats.netResult(), actual.netResult());
        verify(emitStatsUseCase, times(1)).emitStats(any());
    }


    @Test
    void givenInvalidUserId_whenReadUserStats_thenReturnErrorResponse() {
        when(emitStatsUseCase.emitStats(any(EmitStatsQuery.class)))
                .thenReturn(Result.failure(ErrorWrapper.INVALID_USER_ID));

        ResponseEntity<?> response = statsController.readUserStats("user");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ErrorWrapper.INVALID_USER_ID.getMessage(), response.getBody());
        verify(emitStatsUseCase, times(1)).emitStats(any());
    }

    @Test
    void givenValidRequest_whenReadOverviewStats_thenReturnOkResponse() {
        OverviewStats overviewStats = new OverviewStats(5L, 1L, 100.0, 20.0);

        when(emitStatsUseCase.emitStats(any())).thenAnswer(invocation -> Result.success(overviewStats));

        ResponseEntity<?> response = statsController.readOverviewStats();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Object body = response.getBody();
        assertInstanceOf(OverviewStatsResponse.class, body);
        OverviewStatsResponse actual = (OverviewStatsResponse) body;
        assertEquals(overviewStats.totalGames(), actual.totalGames());
        assertEquals(overviewStats.totalPlayers(), actual.totalPlayers());
        assertEquals(overviewStats.totalBet(), actual.totalBet());
        assertEquals(overviewStats.houseProfit(), actual.houseProfit());
        verify(emitStatsUseCase, times(1)).emitStats(any());
    }

    @Test
    void givenFailureInOverviewStats_whenReadOverviewStats_thenReturnErrorResponse() {
        when(emitStatsUseCase.emitStats(any(EmitStatsQuery.class)))
                .thenReturn(Result.failure(ErrorWrapper.INVALID_STATS_OPTION));

        ResponseEntity<?> response = statsController.readOverviewStats();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ErrorWrapper.INVALID_STATS_OPTION.getMessage(), response.getBody());
        verify(emitStatsUseCase, times(1)).emitStats(any());
    }
}
