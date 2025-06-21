package de.htwberlin.casino.blackjack.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Game {
    @Id
    private UUID id;
}
