package de.htwberlin.casino.blackjack.application.domain.service.calculateChances;

/**
 * Record class holding chances for next turn in ongoing blackjack game.
 *
 * @param bust chance to bust when drawing a card in the next turn
 * @param blackjack chance to have a blackjack when drawing a card in the next turn
 */
public record Chances(double bust, double blackjack) {
}
