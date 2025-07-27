package de.htwberlin.casino.blackjack.application.port.in.emitRules;

import de.htwberlin.casino.blackjack.application.domain.model.rules.RuleOption;

/**
 * Query to retrieve the rules associated with a specific {@link RuleOption}.
 *
 * @param option the {@link RuleOption} for which the rules should be emitted.
 */
public record EmitRulesQuery(RuleOption option) {
}
