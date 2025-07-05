package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.model.RuleOption;
import de.htwberlin.casino.blackjack.application.domain.model.Rules;
import org.springframework.stereotype.Component;

@Component
public class RulesMapper {

    public Rules mapToDomainEntity(RuleOption ruleOption, String rules) {
        return new Rules(ruleOption, rules);
    }
}
