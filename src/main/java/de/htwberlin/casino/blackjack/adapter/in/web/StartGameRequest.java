package de.htwberlin.casino.blackjack.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StartGameRequest(@JsonProperty("userId")String userId, @JsonProperty("bet")double bet) {}
