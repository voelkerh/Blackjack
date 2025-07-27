package de.htwberlin.casino.blackjack.adapter.in.web;

/**
 * Record defining output format for chances controller.
 *
 * @param bustChance
 * @param blackjackChance
 */
public record ChancesResponse(double bustChance, double blackjackChance) {
}
