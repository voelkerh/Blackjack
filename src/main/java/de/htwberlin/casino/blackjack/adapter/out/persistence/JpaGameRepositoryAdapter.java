package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.model.RuleOption;
import de.htwberlin.casino.blackjack.application.domain.model.Rules;
import de.htwberlin.casino.blackjack.application.port.out.LoadGameStatePort;
import de.htwberlin.casino.blackjack.application.port.out.LoadRulesPort;
import de.htwberlin.casino.blackjack.application.port.out.LoadStatsPort;
import de.htwberlin.casino.blackjack.application.port.out.ModifyGameStatePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
class JpaGameRepositoryAdapter implements LoadRulesPort, LoadStatsPort, LoadGameStatePort, ModifyGameStatePort {

    private final JpaGameRepository jpaRepository;
    private final JpaRulesRepository jpaRulesRepository;

    @Override
    public Rules retrieveRules(RuleOption option) {
        return null;
    }
}
