package de.htwberlin.casino.blackjack.adapter.out.persistence;

import java.io.Serializable;

public class DrawnCardId implements Serializable {
    private final Long gameId;
    private final Long cardId;

    public DrawnCardId(Long gameId, Long cardId) {
        this.gameId = gameId;
        this.cardId = cardId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DrawnCardId other) {
            return gameId.equals(other.gameId) && cardId.equals(other.cardId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return gameId.hashCode() + cardId.hashCode();
    }
}
