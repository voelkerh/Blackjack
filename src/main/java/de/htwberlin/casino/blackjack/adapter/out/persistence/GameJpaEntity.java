package de.htwberlin.casino.blackjack.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

/**
 * JPA entity representing a single game instance.
 * Stores the user, current game state, users bet, and drawn cards.
 */
@Entity(name = "game")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GameJpaEntity {

    /**
     * Unique identifier for the game.
     */
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

    /**
     * ID of the user who is playing the game.
     */
    @Column(name="userId", nullable = false)
    private String userId;

    /**
     * Current state of the game (e.g., "PLAYING", "WIN", "LOSS", "PUSH").
     */
    @Column(name = "game_state", nullable = false)
    private String gameState;

    /**
     * Cards drawn during the game, associated with either player or dealer.
     */
    @OneToMany(mappedBy = "gameId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DrawnCardJpaEntity> drawnCards = new ArrayList<>();

    /**
     * The amount the user bet in this game.
     */
    @Column(name = "bet")
    private double bet;
}
