package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Rank;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Suit;
import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameImpl;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameState;
import de.htwberlin.casino.blackjack.application.domain.model.hands.HandType;
import de.htwberlin.casino.blackjack.application.domain.model.rules.RuleOption;
import de.htwberlin.casino.blackjack.application.domain.model.rules.Rules;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JpaRepositoryAdapterTest {

    @Mock
    JpaGameRepository gameRepository;
    @Mock
    JpaRulesRepository rulesRepository;
    @Mock
    JpaCardRepository cardRepository;
    @Mock
    JpaDrawnCardsRepository drawnCardsRepository;
    @Mock
    GameMapper gameMapper;
    @Mock
    RulesMapper rulesMapper;
    @Mock
    EntityManager entityManager;

    @InjectMocks
    JpaRepositoryAdapter adapter;

    @BeforeEach
    void setup() {
        adapter = new JpaRepositoryAdapter(gameRepository, rulesRepository, cardRepository, drawnCardsRepository, gameMapper, rulesMapper);

        try {
            Field entityManagerField = JpaRepositoryAdapter.class.getDeclaredField("entityManager");
            entityManagerField.setAccessible(true);
            entityManagerField.set(adapter, entityManager);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject mock EntityManager", e);
        }
    }


    @Test
    void retrieveRules_shouldReturnMappedRules_whenRulesExist() {
        RulesJpaEntity rulesEntity = new RulesJpaEntity();
        Rules expectedRules = mock(Rules.class);

        when(rulesRepository.findById("GENERAL")).thenReturn(Optional.of(rulesEntity));
        when(rulesMapper.mapToDomainEntity(rulesEntity)).thenReturn(expectedRules);

        Rules actual = adapter.retrieveRules(RuleOption.GENERAL);

        assertEquals(expectedRules, actual);
    }

    @Test
    void retrieveRules_shouldThrowException_whenRulesNotFound() {
        when(rulesRepository.findById("GENERAL")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> adapter.retrieveRules(RuleOption.GENERAL));
    }

    @Test
    void retrieveGame_shouldReturnMappedGame_whenFound() {
        GameJpaEntity entity = new GameJpaEntity();
        GameImpl expectedGame = mock(GameImpl.class);

        when(gameRepository.findByIdWithDrawnCards(1L)).thenReturn(Optional.of(entity));
        when(gameMapper.mapToDomainEntity(entity)).thenReturn(expectedGame);

        GameImpl result = adapter.retrieveGame(1L);
        assertEquals(expectedGame, result);
    }

    @Test
    void retrieveGame_shouldThrow_whenNotFound() {
        when(gameRepository.findByIdWithDrawnCards(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> adapter.retrieveGame(1L));
    }

    @Test
    void saveGame_shouldSaveAndReturnMappedGame() {
        GameImpl game = mock(GameImpl.class);
        GameJpaEntity entity = new GameJpaEntity();
        entity.setId(42L);
        GameImpl expected = mock(GameImpl.class);

        when(gameMapper.mapToJpaEntity(eq(game), any())).thenReturn(entity);
        when(gameRepository.saveAndFlush(entity)).thenReturn(entity);
        when(gameMapper.mapToDomainEntity(entity)).thenReturn(expected);

        Game result = adapter.saveGame(game);
        assertEquals(expected, result);
    }

    @Test
    void saveGame_shouldThrow_whenGameIdIsNull() {
        Game game = mock(Game.class);
        GameJpaEntity entity = new GameJpaEntity();

        when(gameMapper.mapToJpaEntity(eq(game), any())).thenReturn(entity);
        when(gameRepository.saveAndFlush(entity)).thenReturn(entity);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> adapter.saveGame(game));
        assertTrue(ex.getMessage().contains("gameId is null"));
    }

    @Test
    void saveCardDraw_shouldSaveCardDraw() {
        Card card = new Card(Rank.ACE, Suit.SPADES);
        CardJpaEntity cardJpa = new CardJpaEntity();
        GameJpaEntity gameJpa = new GameJpaEntity();

        when(cardRepository.findBySuitAndRank(card.suit(), card.rank())).thenReturn(Optional.of(cardJpa));
        when(gameRepository.findById(1L)).thenReturn(Optional.of(gameJpa));

        adapter.saveCardDraw(1L, card, HandType.PLAYER);

        verify(drawnCardsRepository).saveAndFlush(any(DrawnCardJpaEntity.class));
        verify(entityManager).flush();
        verify(entityManager).clear();
    }

    @Test
    void saveCardDraw_shouldThrow_whenCardNotFound() {
        Card card = new Card(Rank.TEN, Suit.HEARTS);
        when(cardRepository.findBySuitAndRank(card.suit(), card.rank())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> adapter.saveCardDraw(1L, card, HandType.DEALER));
    }

    @Test
    void saveCardDraw_shouldThrow_whenGameNotFound() {
        Card card = new Card(Rank.TEN, Suit.HEARTS);
        CardJpaEntity cardJpa = new CardJpaEntity();
        when(cardRepository.findBySuitAndRank(card.suit(), card.rank())).thenReturn(Optional.of(cardJpa));
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> adapter.saveCardDraw(1L, card, HandType.DEALER));
    }

    @Test
    void updateGameState_shouldUpdateAndFlush() {
        GameJpaEntity game = new GameJpaEntity();
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        adapter.updateGameState(1L, GameState.BLACKJACK);

        assertEquals(GameState.BLACKJACK, game.getGameState());
        verify(gameRepository).save(game);
        verify(entityManager).flush();
        verify(entityManager).clear();
    }

    @Test
    void updateGameState_shouldThrow_whenGameNotFound() {
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> adapter.updateGameState(1L, GameState.LOST));
    }

    @Test
    void retrieveTotalGames_shouldReturnFromRepoCorrectly() {
        when(gameRepository.fetchTotalGames()).thenReturn(12L);
        assertEquals(12L, adapter.retrieveTotalGames());
    }

    @Test
    void retrieveTotalPlayers_shouldReturnFromRepoCorrectly() {
        when(gameRepository.fetchTotalPlayers()).thenReturn(5L);
        assertEquals(5L, adapter.retrieveTotalPlayers());
    }

    @Test
    void retrieveTotalBet_shouldReturnFromRepoCorrectly() {
        when(gameRepository.fetchTotalBet()).thenReturn(200.0);
        assertEquals(200.0, adapter.retrieveTotalBet());
    }

    @Test
    void retrieveHouseProfit_shouldReturnFromRepoCorrectly() {
        when(gameRepository.fetchHouseProfit()).thenReturn(50.0);
        assertEquals(50.0, adapter.retrieveHouseProfit());
    }

    @Test
    void retrieveNumberOfGamesPlayedByUser_shouldReturnCorrectly() {
        when(gameRepository.fetchNumberOfGamesPlayedByUser("abc")).thenReturn(3L);
        assertEquals(3L, adapter.retrieveNumberOfGamesPlayedByUser("abc"));
    }

    @Test
    void retrieveNumberOfGamesWithGameStateOfUser_shouldReturnCorrectly() {
        when(gameRepository.fetchNumberOfGamesWithGameStateOfUser("abc", GameState.LOST)).thenReturn(2L);
        assertEquals(2L, adapter.retrieveNumberOfGamesWithGameSateOfUser("abc", GameState.LOST));
    }

    @Test
    void retrieveTotalBetByUser_shouldReturnCorrectly() {
        when(gameRepository.fetchTotalBetByUser("abc")).thenReturn(180.0);
        assertEquals(180.0, adapter.retrieveTotalBetByUser("abc"));
    }

    @Test
    void retrieveWinningsByUser_shouldReturnCorrectly() {
        when(gameRepository.fetchWinningsByUser("abc")).thenReturn(95.0);
        assertEquals(95.0, adapter.retrieveWinningsByUser("abc"));
    }
}
