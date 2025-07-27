package de.htwberlin.casino.blackjack.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity representing a card that has been drawn in a specific game.
 * This entity uses a composite key of {@code gameId} and {@code cardId}.
 */
@Entity(name = "drawn_cards")
@Getter
@Setter
@NoArgsConstructor
public class DrawnCardJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private GameJpaEntity game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "card_suit", referencedColumnName = "suit"),
            @JoinColumn(name = "card_rank", referencedColumnName = "rank")
    })
    private CardJpaEntity card;

    @Column(name = "holder", nullable = false)
    private String holder;

    public DrawnCardJpaEntity(GameJpaEntity game, CardJpaEntity card, String holder) {
        this.game = game;
        this.card = card;
        this.holder = holder;
    }
}
