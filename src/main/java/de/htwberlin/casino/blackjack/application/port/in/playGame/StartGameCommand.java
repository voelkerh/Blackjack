package de.htwberlin.casino.blackjack.application.port.in.playGame;

public record StartGameCommand(String userId, int bet) {}
