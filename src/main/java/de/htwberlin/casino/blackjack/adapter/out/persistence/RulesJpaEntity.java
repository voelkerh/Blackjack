package de.htwberlin.casino.blackjack.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rules")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RulesJpaEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column private String option;
    @Column private String rules;
}
