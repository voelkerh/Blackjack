package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.model.rules.RuleOption;
import de.htwberlin.casino.blackjack.application.domain.model.rules.Rules;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RulesMapperTest {

    @Test
    public void givenGeneralRuleOptionAndString_whenMapToDomainEntity_thenReturnNotNull() {
        RulesMapper mapper = new RulesMapper();

        Rules result = mapper.mapToDomainEntity(RuleOption.GENERAL, "General Rules");

        Assertions.assertNotNull(result);
    }

    @Test
    public void givenGeneralRuleOptionAndString_whenMapToDomainEntity_thenReturnRules() {
        RulesMapper mapper = new RulesMapper();

        Object result = mapper.mapToDomainEntity(RuleOption.GENERAL, "General Rules");

        Assertions.assertInstanceOf(Rules.class, result);
    }

    @Test
    public void givenGeneralRuleOptionAndString_whenMapToDomainEntity_thenSetCorrectValues() {
        RulesMapper mapper = new RulesMapper();

        Rules result = mapper.mapToDomainEntity(RuleOption.GENERAL, "General Rules");

        Assertions.assertEquals(RuleOption.GENERAL, result.option());
        Assertions.assertEquals("General Rules", result.rulesText());
    }

}