package de.htwberlin.casino.blackjack.application.port.in.playGame;

/**
 * Command to perform a "hit" action in a blackjack game (i.e. draw another card for player).
 *
 * @param gameId the ID of the game where the action is to be applied.
 */
public record HitCommand(Long gameId) {}
