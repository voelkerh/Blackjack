package de.htwberlin.casino.blackjack.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request body defining user input format to start a new game via the game controller.
 *
 * @param userId
 * @param bet
 */
public record StartGameRequest(@JsonProperty("userId")String userId, @JsonProperty("bet")double bet) {}
