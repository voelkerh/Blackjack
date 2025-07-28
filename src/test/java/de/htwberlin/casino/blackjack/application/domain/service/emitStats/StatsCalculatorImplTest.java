package de.htwberlin.casino.blackjack.application.domain.service.emitStats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StatsCalculatorImplTest {

    private StatsCalculatorImpl statsCalculator;

    @BeforeEach
    void setUp() {
        statsCalculator = new StatsCalculatorImpl();
    }

    @Test
    void calculateOverviewStats_ReturnsCorrectStats() {
        Long totalGames = 100L;
        Long totalPlayers = 10L;
        double totalBet = 5000.0;
        double houseProfit = 1200.0;

        OverviewStats stats = statsCalculator.calculateOverviewStats(totalGames, totalPlayers, totalBet, houseProfit);

        assertNotNull(stats);
        assertEquals(totalGames, stats.totalGames());
        assertEquals(totalPlayers, stats.totalPlayers());
        assertEquals(totalBet, stats.totalBet());
        assertEquals(houseProfit, stats.houseProfit());
    }

    @Test
    void calculateUserStats_ReturnsCorrectWinRatioAndNetResult() {
        Long gamesPlayed = 50L;
        Long gamesWon = 20L;
        Long gamesLost = 25L;
        Long gamesPushed = 3L;
        Long gamesBlackjack = 2L;
        double totalBet = 1500.0;
        double totalWinnings = 1800.0;

        UserStats stats = statsCalculator.calculateUserStats(
                gamesPlayed, gamesWon, gamesLost, gamesPushed, gamesBlackjack, totalBet, totalWinnings);

        assertNotNull(stats);
        assertEquals(gamesPlayed, stats.gamesPlayed());
        assertEquals("2:20:25:3", stats.winRatio());
        assertEquals(totalBet, stats.totalBet());
        assertEquals(300.0, stats.netResult());
    }
}