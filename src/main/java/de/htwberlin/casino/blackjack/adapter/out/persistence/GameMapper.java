package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.model.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameMapper {

    public Game mapToDomainEntity(GameJpaEntity gameJpaEntity) {
        return new Game(gameJpaEntity.getId(), gameJpaEntity.getUserId(), CardDeck.getInstance(), mapToPlayerHand(gameJpaEntity), mapToDealerHand(gameJpaEntity), GameState.valueOf(gameJpaEntity.getGameState()), gameJpaEntity.getBet());
    }

    private PlayerHand mapToPlayerHand(GameJpaEntity gameJpaEntity) {
        List<DrawnCardJpaEntity> playerHandCards = gameJpaEntity.getPlayerHand();
        if (playerHandCards.size() < 2) {
            throw new IllegalStateException("Invalid number of cards to map hands");
        }
        List<Card> cards = playerHandCards.stream()
                .map(drawn -> {
                    CardJpaEntity c = drawn.getCardId();
                    return new Card(Rank.valueOf(c.getRank()), Suit.valueOf(c.getSuit()));
                })
                .toList();
        return new PlayerHand(cards);
    }

    private DealerHand mapToDealerHand(GameJpaEntity gameJpaEntity) {
        List<DrawnCardJpaEntity> dealerHandCard = gameJpaEntity.getDealerHand();
        if (dealerHandCard.isEmpty()) {
            throw new IllegalStateException("Invalid number of cards to map hands");
        }
        CardJpaEntity cardJpa = dealerHandCard.getFirst().getCardId();
        Card card = new Card(Rank.valueOf(cardJpa.getRank()), Suit.valueOf(cardJpa.getSuit()));
        return new DealerHand(card);
    }

}
