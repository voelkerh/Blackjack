package de.htwberlin.casino.blackjack.application.domain.service.emitStats;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link StatsCalculator} to provide methods to calculate various statistical metrics
 * for the blackjack service.
 */
@Service
@RequiredArgsConstructor
public class StatsCalculatorImpl implements StatsCalculator{
    @Override
    public OverviewStats calculateOverviewStats(Long totalGames, Long totalPlayers, double totalBet, double houseProfit) {
        return new OverviewStats(totalGames, totalPlayers, totalBet, houseProfit);
    }
    @Override
    public UserStats calculateUserStats(Long gamesPlayed, Long gamesWon, Long gamesLost, Long gamesPushed, double totalBet, double totalWinnings) {
        String winRatio = gamesWon + ":" + gamesLost + ":" + gamesPushed;
        double netResult = totalWinnings - totalBet;

        return new UserStats(gamesPlayed, winRatio, totalBet, netResult);
    }
}
