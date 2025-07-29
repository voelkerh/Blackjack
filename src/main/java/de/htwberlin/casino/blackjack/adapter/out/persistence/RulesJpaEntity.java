package de.htwberlin.casino.blackjack.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * JPA entity representing a set of game rules stored in the database.
 */
@Entity(name = "rules")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RulesJpaEntity {
    /**
     * The unique option name/key for this set of rules.
     */
    @Id
    @Column
    private String option;

    /**
     * The text content of the rules associated with this option.
     * Stored as a {@code TEXT} type in the database, cannot be null,
     * and cannot be updated once saved.
     */
    @Column(
            name = "rules_text",
            nullable = false,
            updatable = false,
            columnDefinition = "TEXT"
    )
    private String rules;
}
