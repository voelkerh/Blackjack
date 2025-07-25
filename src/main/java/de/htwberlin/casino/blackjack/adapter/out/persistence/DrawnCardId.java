package de.htwberlin.casino.blackjack.adapter.out.persistence;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DrawnCardId implements Serializable {
    private Long gameId;
    private Long cardId;
}
