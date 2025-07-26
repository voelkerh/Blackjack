package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.factory.hands.HandFactory;
import de.htwberlin.casino.blackjack.application.domain.factory.hands.HandFactoryImpl;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.cards.CardDeckImpl;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Rank;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Suit;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameImpl;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameState;
import de.htwberlin.casino.blackjack.application.domain.model.hands.Hand;
import de.htwberlin.casino.blackjack.application.domain.model.hands.HandType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameMapper {
    HandFactory factory = new HandFactoryImpl();

    public GameImpl mapToDomainEntity(GameJpaEntity gameJpaEntity, JpaDrawnCardsRepository drawnCardsRepository) {
        return new GameImpl(gameJpaEntity.getId(), gameJpaEntity.getUserId(), mapToCardDeck(gameJpaEntity, drawnCardsRepository), mapToHand(HandType.PLAYER, gameJpaEntity), mapToHand(HandType.DEALER, gameJpaEntity), GameState.valueOf(gameJpaEntity.getGameState()), gameJpaEntity.getBet());
    }

    private <T extends Hand> T mapToHand(HandType type, GameJpaEntity gameJpaEntity) {
        List<DrawnCardJpaEntity> drawnCards = (type == HandType.DEALER) ? gameJpaEntity.getDealerHand() : gameJpaEntity.getPlayerHand();

        List<Card> cards = drawnCards.stream()
                .map(drawn -> {
                    CardJpaEntity c = drawn.getCardId();
                    return new Card(Rank.valueOf(c.getRank()), Suit.valueOf(c.getSuit()));
                })
                .toList();

        return factory.create(type, cards);
    }

    private CardDeckImpl mapToCardDeck(GameJpaEntity gameJpaEntity, JpaDrawnCardsRepository drawnCardsRepository) {
        List<DrawnCardJpaEntity> drawnCardsJpa = drawnCardsRepository.findByGameId(gameJpaEntity);

        List<Card> drawnCards = drawnCardsJpa.stream()
                .map(drawnCard -> {
                    CardJpaEntity cardJpa = drawnCard.getCardId();
                    return new Card(Rank.valueOf(cardJpa.getRank()), Suit.valueOf(cardJpa.getSuit()));
                })
                .toList();

        return new CardDeckImpl(drawnCards);
    }

}
