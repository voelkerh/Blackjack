package de.htwberlin.casino.blackjack.adapter.out.persistence;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Composite primary key class for {@link DrawnCardJpaEntity}.
 * Represents a card drawn in a specific game.
 * Follows pattern from <a href="https://www.baeldung.com/jpa-composite-primary-keys">baeldung.com</a> (last access on 26.07.2025)
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class DrawnCardId implements Serializable {
    /**
     * ID of the game.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private GameJpaEntity gameId;

    /**
     * ID of the Card (e.g. Suit and Rank).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "card_suit", referencedColumnName = "suit", insertable = false, updatable = false),
            @JoinColumn(name = "card_rank", referencedColumnName = "rank", insertable = false, updatable = false)
    })
    private CardJpaEntity cardId;
}
