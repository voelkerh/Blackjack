package de.htwberlin.casino.blackjack.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class CardId {
    private String suit;
    private String rank;
}
