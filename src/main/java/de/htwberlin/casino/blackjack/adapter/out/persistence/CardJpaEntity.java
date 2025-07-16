package de.htwberlin.casino.blackjack.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "cards")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CardJpaEntity {
    @Id
    @SequenceGenerator(
            name = "card_sequence",
            sequenceName = "card_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "card_sequence"
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "suit", nullable = false)
    private String suit;

    @Column(name = "rank", nullable = false)
    private String rank;
}
