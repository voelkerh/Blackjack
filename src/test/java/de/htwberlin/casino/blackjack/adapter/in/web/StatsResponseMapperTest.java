package de.htwberlin.casino.blackjack.adapter.in.web;

import de.htwberlin.casino.blackjack.application.domain.service.emitStats.OverviewStats;
import de.htwberlin.casino.blackjack.application.domain.service.emitStats.UserStats;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatsResponseMapperTest {

    @Test
    void toUserStatsResponse_validInput_returnsResponse() {
        UserStats input = new UserStats(10L, "3:2:0:0", 100.0, 25.0);

        UserStatsResponse response = StatsResponseMapper.toUserStatsResponse(input);

        assertEquals(10L, response.gamesPlayed());
        assertEquals("3:2:0:0", response.winRatio());
        assertEquals(100.0, response.totalBet());
        assertEquals(25.0, response.netResult());
    }

    @Test
    void toUserStatsResponse_invalidInput_throwsException() {
        Object invalid = new Object();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                StatsResponseMapper.toUserStatsResponse(invalid)
        );

        assertEquals("Expected UserStats but got Object", exception.getMessage());
    }

    @Test
    void toOverviewStatsResponse_validInput_returnsResponse() {
        OverviewStats input = new OverviewStats(100L, 50L, 5000.0, 1500.0);

        OverviewStatsResponse response = StatsResponseMapper.toOverviewStatsResponse(input);

        assertEquals(100L, response.totalGames());
        assertEquals(50L, response.totalPlayers());
        assertEquals(5000.0, response.totalBet());
        assertEquals(1500.0, response.houseProfit());
    }

    @Test
    void toOverviewStatsResponse_invalidInput_throwsException() {
        Object invalid = new Object();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                StatsResponseMapper.toOverviewStatsResponse(invalid)
        );

        assertEquals("Expected OverviewStats but got Object", exception.getMessage());
    }
}
