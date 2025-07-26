package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.model.rules.RuleOption;
import de.htwberlin.casino.blackjack.application.domain.model.rules.Rules;
import org.springframework.stereotype.Component;

/**
 * Mapper that transforms elements of {@link RulesJpaEntity} to application domain entity {@link Rules}.
 */
@Component
public class RulesMapper {

    /**
     * Transforms elements of {@link RulesJpaEntity} to application domain entity {@link Rules}.
     *
     * @param ruleOption containing the ruleOption retrieved from the database
     * @param rules containing rules text from the database
     * @return {@link Rules} domain entity object to be delegated to application service
     */
    public Rules mapToDomainEntity(RuleOption ruleOption, String rules) {
        return new Rules(ruleOption, rules);
    }
}
