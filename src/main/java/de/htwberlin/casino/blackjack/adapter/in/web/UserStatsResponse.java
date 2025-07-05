package de.htwberlin.casino.blackjack.adapter.in.web;

public record UserStatsResponse(int gamesPlayed, String winRatio, int totalBet, int netResult) {
}

