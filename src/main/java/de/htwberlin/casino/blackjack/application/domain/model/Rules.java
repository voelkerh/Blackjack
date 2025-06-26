package de.htwberlin.casino.blackjack.application.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rules {

    @Id
    @Enumerated(EnumType.STRING)
    private RuleOption option;

    private String rule;

}
