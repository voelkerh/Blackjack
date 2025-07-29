package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.cards.CardDeckImpl;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Rank;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Suit;
import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameImpl;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameState;
import de.htwberlin.casino.blackjack.application.domain.model.hands.DealerHand;
import de.htwberlin.casino.blackjack.application.domain.model.hands.HandType;
import de.htwberlin.casino.blackjack.application.domain.model.hands.PlayerHand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class GameMapperTest {

    private GameMapper gameMapper;
    private JpaCardRepository cardRepository;

    @BeforeEach
    void setUp() {
        gameMapper = new GameMapper();
        cardRepository = mock(JpaCardRepository.class);
    }

    @Test
    void mapToDomainEntity_shouldMapCorrectly() {
        CardJpaEntity cardJpa = new CardJpaEntity(Rank.KING, Suit.SPADES);
        CardJpaEntity cardJpa2 = new CardJpaEntity(Rank.QUEEN, Suit.SPADES);
        CardJpaEntity cardJpa3 = new CardJpaEntity(Rank.TWO, Suit.SPADES);
        CardJpaEntity cardJpa4 = new CardJpaEntity(Rank.ACE, Suit.SPADES);

        DrawnCardJpaEntity playerDraw1 = new DrawnCardJpaEntity();
        playerDraw1.setHolder(HandType.PLAYER);
        playerDraw1.setCard(cardJpa);

        DrawnCardJpaEntity playerDraw2 = new DrawnCardJpaEntity();
        playerDraw2.setHolder(HandType.PLAYER);
        playerDraw2.setCard(cardJpa2);

        DrawnCardJpaEntity dealerDraw1 = new DrawnCardJpaEntity();
        dealerDraw1.setHolder(HandType.DEALER);
        dealerDraw1.setCard(cardJpa3);

        DrawnCardJpaEntity dealerDraw2 = new DrawnCardJpaEntity();
        dealerDraw2.setHolder(HandType.DEALER);
        dealerDraw2.setCard(cardJpa4);

        GameJpaEntity jpaEntity = new GameJpaEntity();
        jpaEntity.setId(1L);
        jpaEntity.setUserId("user1");
        jpaEntity.setBet(20.0);
        jpaEntity.setGameState(GameState.PLAYING);
        jpaEntity.setDrawnCards(List.of(playerDraw1, playerDraw2, dealerDraw1, dealerDraw2));

        GameImpl game = gameMapper.mapToDomainEntity(jpaEntity);

        assertThat(game.getId()).isEqualTo(1L);
        assertThat(game.getUserId()).isEqualTo("user1");
        assertThat(game.getBet()).isEqualTo(20.0);
        assertThat(game.getGameState()).isEqualTo(GameState.PLAYING);

        assertThat(game.getPlayerHand().getCards()).hasSize(2);
        assertThat(game.getDealerHand().getCards()).hasSize(2);

        Card card = game.getPlayerHand().getCards().getFirst();
        assertThat(card.rank()).isEqualTo(Rank.KING);
        assertThat(card.suit()).isEqualTo(Suit.SPADES);
    }

    @Test
    void mapToJpaEntity_shouldMapCorrectly() {
        Card card = new Card(Rank.KING, Suit.SPADES);
        Card card2 = new Card(Rank.QUEEN, Suit.SPADES);
        Card card3 = new Card(Rank.TWO, Suit.SPADES);
        Card card4 = new Card(Rank.ACE, Suit.SPADES);

        CardJpaEntity cardJpa = new CardJpaEntity(Rank.KING, Suit.SPADES);
        CardJpaEntity cardJpa2 = new CardJpaEntity(Rank.QUEEN, Suit.SPADES);
        CardJpaEntity cardJpa3 = new CardJpaEntity(Rank.TWO, Suit.SPADES);
        CardJpaEntity cardJpa4 = new CardJpaEntity(Rank.ACE, Suit.SPADES);

        when(cardRepository.findByRankAndSuit(Rank.KING, Suit.SPADES))
                .thenReturn(Optional.of(cardJpa));
        when(cardRepository.findByRankAndSuit(Rank.QUEEN, Suit.SPADES))
                .thenReturn(Optional.of(cardJpa2));
        when(cardRepository.findByRankAndSuit(Rank.TWO, Suit.SPADES)).thenReturn(Optional.of(cardJpa3));
        when(cardRepository.findByRankAndSuit(Rank.ACE, Suit.SPADES)).thenReturn(Optional.of(cardJpa4));

        PlayerHand playerHand = new PlayerHand(List.of(card, card2));
        DealerHand dealerHand = new DealerHand(card3, card4);
        GameImpl game = new GameImpl(2L, "user2", new CardDeckImpl(List.of(card, card2, card3, card4)), playerHand, dealerHand, GameState.WON, 99.0);

        GameJpaEntity entity = gameMapper.mapToJpaEntity(game, cardRepository);

        assertThat(entity.getId()).isEqualTo(2L);
        assertThat(entity.getUserId()).isEqualTo("user2");
        assertThat(entity.getGameState()).isEqualTo(GameState.WON);
        assertThat(entity.getBet()).isEqualTo(99.0);
        assertThat(entity.getDrawnCards()).hasSize(4); // 2 player + 2 dealer
    }

    @Test
    void mapToJpaEntity_shouldThrowException_whenCardNotFound() {
        Card card = new Card(Rank.FIVE, Suit.DIAMONDS);
        Card card2 = new Card(Rank.QUEEN, Suit.SPADES);
        Card card3 = new Card(Rank.TWO, Suit.SPADES);

        when(cardRepository.findByRankAndSuit(Rank.FIVE, Suit.DIAMONDS))
                .thenReturn(Optional.empty());

        Game game = new GameImpl(
                3L,
                "missing",
                new CardDeckImpl(List.of(card, card2, card3)),
                new PlayerHand(List.of(card)),
                new DealerHand(card2, card3),
                GameState.LOST,
                10.0
        );

        // When / Then
        assertThatThrownBy(() -> gameMapper.mapToJpaEntity(game, cardRepository))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Card not found in DB");

        verify(cardRepository).findByRankAndSuit(Rank.FIVE, Suit.DIAMONDS);
    }
}
