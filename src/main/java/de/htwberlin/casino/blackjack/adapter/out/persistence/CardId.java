package de.htwberlin.casino.blackjack.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Composite primary key class for {@link CardJpaEntity}.
 * Represents a unique card of a deck.
 * Follows pattern from <a href="https://www.baeldung.com/jpa-composite-primary-keys">baeldung.com</a> (last access on 26.07.2025)
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class CardId {
    private String suit;
    private String rank;
}
