package de.htwberlin.casino.blackjack.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * JPA entity representing a set of game rules stored in the database.
 * <br>
 * The {@code rules} field is stored as a {@code TEXT} column in the database,
 * and is not allowed to be null or updated once persisted.
 */
@Entity(name = "rules")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RulesJpaEntity {

    @Id
    @Column
    private String option;

    @Column(
            name = "rules_text",
            nullable = false,
            updatable = false,
            columnDefinition = "TEXT"
    )
    private String rules;
}
