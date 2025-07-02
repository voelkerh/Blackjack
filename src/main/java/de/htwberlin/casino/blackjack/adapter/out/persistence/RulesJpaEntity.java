package de.htwberlin.casino.blackjack.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity(name = "rules")
@AllArgsConstructor
@NoArgsConstructor
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
