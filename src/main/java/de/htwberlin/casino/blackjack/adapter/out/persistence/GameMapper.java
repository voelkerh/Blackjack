package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.factory.hands.HandFactory;
import de.htwberlin.casino.blackjack.application.domain.factory.hands.HandFactoryImpl;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.cards.CardDeckImpl;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Rank;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Suit;
import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameImpl;
import de.htwberlin.casino.blackjack.application.domain.model.hands.DealerHand;
import de.htwberlin.casino.blackjack.application.domain.model.hands.Hand;
import de.htwberlin.casino.blackjack.application.domain.model.hands.HandType;
import de.htwberlin.casino.blackjack.application.domain.model.hands.PlayerHand;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Maps {@link GameJpaEntity} instances to domain model {@link GameImpl} instances.
 * Also handles conversion of drawn cards into hands and decks using {@link HandFactory}.
 * Follows pattern from Page 90f. Hombergs, Clean Architecture, 2024
 */
@Component
public class GameMapper {
    HandFactory factory = new HandFactoryImpl();

    /**
     * Maps a {@link GameJpaEntity} and its related drawn cards to a {@link GameImpl} domain object.
     *
     * @param gameJpaEntity the persisted game entity
     * @return a fully constructed domain {@link GameImpl} instance
     */
    public GameImpl mapToDomainEntity(GameJpaEntity gameJpaEntity) {
        List<DrawnCardJpaEntity> drawnCards = gameJpaEntity.getDrawnCards();
        PlayerHand playerHand = mapToDomainEntity(HandType.PLAYER, drawnCards);
        DealerHand dealerHand = mapToDomainEntity(HandType.DEALER, drawnCards);
        return new GameImpl(gameJpaEntity.getId(), gameJpaEntity.getUserId(), mapToDomainEntity(drawnCards), playerHand, dealerHand, gameJpaEntity.getGameState(), gameJpaEntity.getBet());
    }

    private <T extends Hand> T mapToDomainEntity(HandType type, List<DrawnCardJpaEntity> drawnCards) {
        List<DrawnCardJpaEntity> filtered = drawnCards.stream()
                .filter(card -> type.equals(card.getHolder()))
                .toList();

        List<Card> cards = filtered.stream()
                .map(drawn -> {
                    CardJpaEntity c = drawn.getCard();
                    return new Card(Rank.valueOf(c.getRank()), Suit.valueOf(c.getSuit()));
                })
                .toList();

        return factory.create(type, cards);
    }

    private CardDeckImpl mapToDomainEntity(List<DrawnCardJpaEntity> drawnCardsJpa) {
        List<Card> drawnCards = drawnCardsJpa.stream()
                .map(drawnCard -> {
                    CardJpaEntity cardJpa = drawnCard.getCard();
                    return new Card(Rank.valueOf(cardJpa.getRank()), Suit.valueOf(cardJpa.getSuit()));
                })
                .toList();

        return new CardDeckImpl(drawnCards);
    }

    // JpaCardRepo necessary because we only create each Card once so they cannot be recreated - it has to be the originals from the repo
    public GameJpaEntity mapToJpaEntity(Game game, JpaCardRepository cardRepository) {
        GameJpaEntity entity = new GameJpaEntity();
        entity.setId(game.getId()); // can be null for new games
        entity.setUserId(game.getUserId());
        entity.setGameState(game.getGameState());
        entity.setBet(game.getBet());

        List<DrawnCardJpaEntity> drawnCards = new ArrayList<>();
        drawnCards.addAll(mapToJpaEntity(HandType.PLAYER, game.getPlayerHand().getCards(), entity, cardRepository));
        drawnCards.addAll(mapToJpaEntity(HandType.DEALER, game.getDealerHand().getCards(), entity, cardRepository));

        entity.setDrawnCards(drawnCards);

        return entity;
    }

    private List<DrawnCardJpaEntity> mapToJpaEntity(HandType type, List<Card> cards, GameJpaEntity entity, JpaCardRepository cardRepository) {
        List<DrawnCardJpaEntity> drawnCards = new ArrayList<>();
        for (Card card : cards) {
            drawnCards.add(new DrawnCardJpaEntity(
                    entity,
                    mapToJpaEntity(card, cardRepository),
                    type
            ));
        }
        return drawnCards;
    }

    private CardJpaEntity mapToJpaEntity(Card card, JpaCardRepository cardRepository) {
        return cardRepository.findBySuitAndRank(card.suit().name(), card.rank().name())
                .orElseThrow(() -> new IllegalArgumentException("Card not found in DB"));
    }
}
