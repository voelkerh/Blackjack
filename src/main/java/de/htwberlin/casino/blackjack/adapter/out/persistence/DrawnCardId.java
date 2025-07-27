package de.htwberlin.casino.blackjack.adapter.out.persistence;

import lombok.*;

import java.io.Serializable;

/**
 * Composite primary key class for {@link DrawnCardJpaEntity}.
 * Consists of {@code gameID} and {@code cardId}.
 * Follows pattern from <a href="https://www.baeldung.com/jpa-composite-primary-keys">baeldung.com</a> (last access on 26.07.2025)
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DrawnCardId implements Serializable {
    /**
     * ID of the game in which the card was drawn.
     */
    private Long gameId;
    /**
     * ID of the drawn card from deck.
     */
    private Long cardId;
}
