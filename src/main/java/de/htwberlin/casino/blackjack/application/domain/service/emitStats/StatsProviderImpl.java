package de.htwberlin.casino.blackjack.application.domain.service.emitStats;

import org.springframework.stereotype.Service;

@Service
public class StatsProviderImpl implements StatsProvider {

    @Override
    public UserStats fetchUserStats(String userId) {
        return null;
    }

    @Override
    public OverviewStats fetchOverviewStats() {
        return null;
    }
}
