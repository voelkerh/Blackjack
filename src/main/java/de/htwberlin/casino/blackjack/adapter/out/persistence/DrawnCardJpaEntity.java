package de.htwberlin.casino.blackjack.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@Entity(name = "drawn_card")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@IdClass(DrawnCardId.class)
@FilterDef(name = "holderFilter", parameters = @ParamDef(name = "holder", type = String.class))
public class DrawnCardJpaEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private GameJpaEntity gameId;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private CardJpaEntity cardId;

    /**
     * Index of card in hand
     */
    @Column(name = "index")
    private int index;

    /**
     * Holder of the card (player or dealer)
     */
    @Column(name = "holder")
    private String holder;
}
