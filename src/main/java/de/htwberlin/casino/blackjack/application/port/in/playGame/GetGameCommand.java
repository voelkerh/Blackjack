package de.htwberlin.casino.blackjack.application.port.in.playGame;

/**
 * Command to retrieve a game instance by its ID.
 *
 * @param gameId the ID of the game to retrieve.
 */
public record GetGameCommand(Long gameId) {}
