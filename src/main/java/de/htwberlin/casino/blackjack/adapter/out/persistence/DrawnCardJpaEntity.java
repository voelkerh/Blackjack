package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.model.hands.HandType;
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
    /**
     * Unique identifier for the drawn card record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The game this drawn card belongs to.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id", nullable = false)
    private GameJpaEntity game;

    /**
     * The specific card that was drawn, identified by suit and rank.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "card_suit", referencedColumnName = "suit"),
            @JoinColumn(name = "card_rank", referencedColumnName = "rank")
    })
    private CardJpaEntity card;

    /**
     * Indicates who holds the card in the game (e.g., PLAYER or DEALER).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "holder", nullable = false)
    private HandType holder;

    /**
     * Constructs a new DrawnCardJpaEntity instance.
     *
     * @param game   the game associated with this drawn card
     * @param card   the card that was drawn
     * @param holder the hand type holding this card (player or dealer)
     */
    public DrawnCardJpaEntity(GameJpaEntity game, CardJpaEntity card, HandType holder) {
        this.game = game;
        this.card = card;
        this.holder = holder;
    }
}
