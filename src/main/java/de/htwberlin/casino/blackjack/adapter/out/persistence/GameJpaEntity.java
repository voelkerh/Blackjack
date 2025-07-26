package de.htwberlin.casino.blackjack.adapter.out.persistence;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "game")
public class GameJpaEntity {

    @Id
    @SequenceGenerator(
            name = "game_sequence",
            sequenceName = "game_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "game_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(name="userId", nullable = false)
    private String userId;

    @Column(name = "game_state", nullable = false)
    private String gameState;

    @Column(name = "bet")
    private double bet;

}
