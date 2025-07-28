package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.model.rules.RuleOption;
import de.htwberlin.casino.blackjack.application.domain.model.rules.Rules;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RulesMapperTest {

    @Test
    public void givenGeneralRuleOptionAndString_whenMapToDomainEntity_thenReturnNotNull() {
        RulesMapper mapper = new RulesMapper();
        RulesJpaEntity rulesJpaEntity = mock(RulesJpaEntity.class);
        when(rulesJpaEntity.getOption()).thenReturn("GENERAL");
        when(rulesJpaEntity.getRules()).thenReturn("General Rules");

        Rules result = mapper.mapToDomainEntity(rulesJpaEntity);

        Assertions.assertNotNull(result);
    }

    @Test
    public void givenGeneralRuleOptionAndString_whenMapToDomainEntity_thenReturnRules() {
        RulesMapper mapper = new RulesMapper();
        RulesJpaEntity rulesJpaEntity = mock(RulesJpaEntity.class);
        when(rulesJpaEntity.getOption()).thenReturn("GENERAL");
        when(rulesJpaEntity.getRules()).thenReturn("General Rules");

        Rules result = mapper.mapToDomainEntity(rulesJpaEntity);

        Assertions.assertInstanceOf(Rules.class, result);
    }

    @Test
    public void givenGeneralRuleOptionAndString_whenMapToDomainEntity_thenSetCorrectValues() {
        RulesMapper mapper = new RulesMapper();
        RulesJpaEntity rulesJpaEntity = mock(RulesJpaEntity.class);
        when(rulesJpaEntity.getOption()).thenReturn("GENERAL");
        when(rulesJpaEntity.getRules()).thenReturn("General Rules");

        Rules result = mapper.mapToDomainEntity(rulesJpaEntity);

        Assertions.assertEquals(RuleOption.GENERAL, result.option());
        Assertions.assertEquals("General Rules", result.rulesText());
    }

    @Test
    public void givenHitRuleOptionAndString_whenMapToDomainEntity_thenSetCorrectValues() {
        RulesMapper mapper = new RulesMapper();
        RulesJpaEntity rulesJpaEntity = mock(RulesJpaEntity.class);
        when(rulesJpaEntity.getOption()).thenReturn("HIT");
        when(rulesJpaEntity.getRules()).thenReturn("Hit Rules");

        Rules result = mapper.mapToDomainEntity(rulesJpaEntity);

        Assertions.assertEquals(RuleOption.HIT, result.option());
        Assertions.assertEquals("Hit Rules", result.rulesText());
    }

    @Test
    public void givenStandRuleOptionAndString_whenMapToDomainEntity_thenSetCorrectValues() {
        RulesMapper mapper = new RulesMapper();
        RulesJpaEntity rulesJpaEntity = mock(RulesJpaEntity.class);
        when(rulesJpaEntity.getOption()).thenReturn("STAND");
        when(rulesJpaEntity.getRules()).thenReturn("Stand Rules");

        Rules result = mapper.mapToDomainEntity(rulesJpaEntity);

        Assertions.assertEquals(RuleOption.STAND, result.option());
        Assertions.assertEquals("Stand Rules", result.rulesText());
    }

}