package de.htwberlin.casino.blackjack.application.port.out;

import de.htwberlin.casino.blackjack.application.domain.model.rules.RuleOption;
import de.htwberlin.casino.blackjack.application.domain.model.rules.Rules;
import jakarta.persistence.EntityNotFoundException;

/**
 * Outbound port defining the methods to retrieve rules from persistent data source.
 * Implemented from an outbound adapter with database access.
 */
public interface LoadRulesPort {

    /**
     * Retrieves rules from database specified by {@link RuleOption}.
     *
     * @param option containing option to retrieve rules for
     * @return Rules containing {@link RuleOption} and retrieved rules text
     * @throws EntityNotFoundException if the ruleset is not found
     */
    Rules retrieveRules(RuleOption option);
}
