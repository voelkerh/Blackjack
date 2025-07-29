package de.htwberlin.casino.blackjack.application.port.in.playGame;

/**
 * Command to start a new blackjack game for a user with a specified bet amount.
 *
 * @param userId the ID of the user starting the game.
 * @param bet the amount the user is betting in this game.
 */
public record StartGameCommand(String userId, double bet) {}
