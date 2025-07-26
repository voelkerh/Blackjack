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
     * Transforms a {@link RulesJpaEntity} to application domain entity {@link Rules}.
     *
     * @param rulesJpaEntity containing the rules persisted in the database
     * @return {@link Rules} domain entity object to be delegated to application service
     */
    public Rules mapToDomainEntity(RulesJpaEntity rulesJpaEntity) {
        return new Rules(RuleOption.valueOf(rulesJpaEntity.getOption()), rulesJpaEntity.getRules());
    }
}
