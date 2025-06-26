package de.htwberlin.casino.blackjack.application.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rules {

    private Long id;
    private String option;
    private String rule;

}
