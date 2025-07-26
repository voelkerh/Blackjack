package de.htwberlin.casino.blackjack.application.port.in.emitRules;

import de.htwberlin.casino.blackjack.application.domain.model.rules.RuleOption;

public record EmitRulesQuery(RuleOption option) {
}
