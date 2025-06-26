package de.htwberlin.casino.blackjack.application.port.out;

import de.htwberlin.casino.blackjack.application.domain.model.RuleOption;
import de.htwberlin.casino.blackjack.application.domain.model.Rules;

public interface LoadRulesPort {
    Rules retrieveRules(RuleOption option);
}
