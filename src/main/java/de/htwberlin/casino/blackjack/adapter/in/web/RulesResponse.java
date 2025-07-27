package de.htwberlin.casino.blackjack.adapter.in.web;

/**
 * Record defining output format for rules retrieval with rules controller.
 *
 * @param option
 * @param rulesText
 */
public record RulesResponse(String option, String rulesText) {
}
