package de.htwberlin.casino.blackjack.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "game")
@AllArgsConstructor
@NoArgsConstructor
@Getter
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
    private Long userId;

    @Column(name = "game_state", nullable = false)
    private String gameState;

    @OneToMany(mappedBy = "gameId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DrawnCardJpaEntity> drawnCards = new ArrayList<>();

    @Column(name = "bet")
    private double bet;

    public List<DrawnCardJpaEntity> getPlayerHand() {
        return drawnCards.stream()
                .filter(card -> "player".equals(card.getHolder()))
                .toList();
    }

    public List<DrawnCardJpaEntity> getDealerHand() {
        return drawnCards.stream()
                .filter(card -> "dealer".equals(card.getHolder()))
                .toList();
    }
}
