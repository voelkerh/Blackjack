package de.htwberlin.casino.blackjack.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class GameJpaEntity {

    @Id
    private UUID id;
}
