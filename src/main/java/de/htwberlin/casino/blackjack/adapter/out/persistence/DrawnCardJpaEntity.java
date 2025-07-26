package de.htwberlin.casino.blackjack.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

/**
 * JPA entity representing a card that has been drawn in a specific game.
 * This entity uses a composite key of {@code gameId} and {@code cardId}.
 */
@Entity(name = "drawn_card")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@IdClass(DrawnCardId.class)
@FilterDef(name = "holderFilter", parameters = @ParamDef(name = "holder", type = String.class))
public class DrawnCardJpaEntity {

    /**
     * Reference to the game where the card was drawn.
     */
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private GameJpaEntity gameId;

    /**
     * Reference to the card that was drawn.
     */
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "card_suit", referencedColumnName = "suit", insertable = false, updatable = false),
            @JoinColumn(name = "card_rank", referencedColumnName = "rank", insertable = false, updatable = false)
    })
    private CardJpaEntity cardId;

    /**
     * Holder of the card, e.g., "player" or "dealer".
     */
    @Column(name = "holder")
    private String holder;
}
