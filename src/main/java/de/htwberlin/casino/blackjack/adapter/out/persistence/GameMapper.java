package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.factory.hands.HandFactory;
import de.htwberlin.casino.blackjack.application.domain.factory.hands.HandFactoryImpl;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.cards.CardDeckImpl;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Rank;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Suit;
import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameImpl;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameState;
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
     * @param gameJpaEntity        the persisted game entity
     * @param drawnCardsRepository the repository to load drawn cards
     * @return a fully constructed domain {@link GameImpl} instance
     */
    public GameImpl mapToDomainEntity(GameJpaEntity gameJpaEntity, JpaDrawnCardsRepository drawnCardsRepository) {
        return new GameImpl(gameJpaEntity.getId(), gameJpaEntity.getUserId(), mapToCardDeck(gameJpaEntity, drawnCardsRepository), mapToHand(HandType.PLAYER, gameJpaEntity), mapToHand(HandType.DEALER, gameJpaEntity), GameState.valueOf(gameJpaEntity.getGameState()), gameJpaEntity.getBet());
    }

    /**
     * Maps drawn card JPA entities into a domain {@link Hand}, based on the hand type.
     *
     * @param type          the type of hand to map ("player" or "dealer")
     * @param gameJpaEntity the game entity from which to extract hand information
     * @param <T>           the concrete subclass of {@link Hand} (e.g., {@code DealerHand}, {@code PlayerHand})
     * @return the constructed {@link DealerHand} or {@link PlayerHand} instance
     */
    private <T extends Hand> T mapToHand(HandType type, GameJpaEntity gameJpaEntity) {
        List<DrawnCardJpaEntity> drawnCards = (type == HandType.DEALER) ? getDealerHand(gameJpaEntity) : getPlayerHand(gameJpaEntity);

        List<Card> cards = drawnCards.stream()
                .map(drawn -> {
                    CardJpaEntity c = drawn.getCardId();
                    return new Card(Rank.valueOf(c.getRank()), Suit.valueOf(c.getSuit()));
                })
                .toList();

        return factory.create(type, cards);
    }

    /**
     * Maps drawn cards from the repository to a domain {@link CardDeckImpl} by removing all dealt cards.
     *
     * @param gameJpaEntity        the game to retrieve drawn cards for
     * @param drawnCardsRepository the repository to load drawn card entities
     * @return the constructed {@link CardDeckImpl} with removed drawn cards
     */
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

    /**
     * Retrieves all cards from a game that were drawn by the player.
     *
     * @param gameJpaEntity the game from which to extract the player's hand
     * @return list of drawn cards held by the player
     */
    public List<DrawnCardJpaEntity> getPlayerHand(GameJpaEntity gameJpaEntity) {
        return gameJpaEntity.getDrawnCards().stream()
                .filter(card -> "player".equals(card.getHolder()))
                .toList();
    }

    /**
     * Retrieves all cards from a game that were drawn by the dealer.
     *
     * @param gameJpaEntity the game from which to extract the dealer's hand
     * @return list of drawn cards held by the dealer
     */
    public List<DrawnCardJpaEntity> getDealerHand(GameJpaEntity gameJpaEntity) {
        return gameJpaEntity.getDrawnCards().stream()
                .filter(card -> "dealer".equals(card.getHolder()))
                .toList();
    }

    public GameJpaEntity mapToJpaEntity(Game game) {
        GameJpaEntity entity = new GameJpaEntity();
        entity.setId(game.getId()); // can be null for new games
        entity.setUserId(game.getUserId());
        entity.setGameState(game.getGameState().toString());
        entity.setBet(game.getBet());

        List<DrawnCardJpaEntity> drawnCards = new ArrayList<>();

        for (Card card : game.getPlayerHand().getCards()) {
            drawnCards.add(new DrawnCardJpaEntity(
                    entity,
                    mapToJpaEntity(card),
                    "player"
            ));
        }

        for (Card card : game.getDealerHand().getCards()) {
            drawnCards.add(new DrawnCardJpaEntity(
                    entity,
                    mapToJpaEntity(card),
                    "dealer"
            ));
        }

        entity.getDrawnCards().addAll(drawnCards);

        return entity;
    }
    public CardJpaEntity mapToJpaEntity(Card card) {
        return new CardJpaEntity(card.suit().name(), card.rank().name());
    }
}
