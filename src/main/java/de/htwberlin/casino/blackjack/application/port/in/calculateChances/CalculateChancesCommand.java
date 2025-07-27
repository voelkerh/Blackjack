package de.htwberlin.casino.blackjack.application.port.in.calculateChances;

/**
 * Command to calculate bust or blackjack chances for a specific game.
 *
 * @param gameId the ID of the game for which the chances are to be calculated.
 */
public record CalculateChancesCommand(Long gameId) {
}
