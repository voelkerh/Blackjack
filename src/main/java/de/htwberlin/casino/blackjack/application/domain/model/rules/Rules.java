package de.htwberlin.casino.blackjack.application.domain.model.rules;

/**
 * Rules record bundling RuleOption and rules text for joint retrieval.
 * @param option
 * @param rulesText
 */
public record Rules (RuleOption option, String rulesText) {
}
