package de.htwberlin.casino.blackjack.adapter.in.web;

/**
 * Request body defining user input format to start a new game via the game controller.
 *
 * @param userID
 * @param bet
 */
public record StartGameRequest(String userID, int bet) {}
