package de.htwberlin.casino.blackjack.application.port.in.playGame;

/**
 * Command to perform a "stand" action in a blackjack game (i.e. player stops taking cards and it's dealers turn).
 *
 * @param gameId the ID of the game where the action is to be applied.
 */
public record StandCommand(Long gameId) {}