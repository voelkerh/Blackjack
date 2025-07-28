package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameImpl;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameState;
import de.htwberlin.casino.blackjack.application.domain.model.hands.HandType;
import de.htwberlin.casino.blackjack.application.domain.model.rules.RuleOption;
import de.htwberlin.casino.blackjack.application.domain.model.rules.Rules;
import de.htwberlin.casino.blackjack.application.port.out.LoadGamePort;
import de.htwberlin.casino.blackjack.application.port.out.LoadRulesPort;
import de.htwberlin.casino.blackjack.application.port.out.LoadStatsPort;
import de.htwberlin.casino.blackjack.application.port.out.ModifyGamePort;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Adapts Spring Data JPA repositories to domain ports.
 * Handles persistence-related operations for rules, stats and games.
 */
@RequiredArgsConstructor
@Repository
class JpaRepositoryAdapter implements LoadRulesPort, LoadStatsPort, LoadGamePort, ModifyGamePort {

    @PersistenceContext
    private EntityManager entityManager;

    private final JpaGameRepository gameRepository;
    private final JpaRulesRepository rulesRepository;
    private final JpaCardRepository cardRepository;
    private final JpaDrawnCardsRepository drawnCardsRepository;
    private final GameMapper gameMapper;
    private final RulesMapper rulesMapper;

    @Override
    public Rules retrieveRules(RuleOption option) {
        RulesJpaEntity rulesJpaEntity = rulesRepository.findById(option.toString()).orElseThrow(EntityNotFoundException::new);
        return rulesMapper.mapToDomainEntity(RuleOption.valueOf(rulesJpaEntity.getOption()), rulesJpaEntity.getRules());
    }

    @Override
    public GameImpl retrieveGame(Long gameId) {
        GameJpaEntity gameJpaEntity = gameRepository.findByIdWithDrawnCards(gameId).orElseThrow(EntityNotFoundException::new);
        return gameMapper.mapToDomainEntity(gameJpaEntity);
    }

    @Override
    public Long retrieveTotalGames() {
        return gameRepository.fetchTotalGames();
    }

    @Override
    public Long retrieveTotalPlayers() {
        return gameRepository.fetchTotalPlayers();
    }

    @Override
    public Double retrieveTotalBet() {
        return gameRepository.fetchTotalBet();
    }

    @Override
    public Double retrieveHouseProfit() {
        return gameRepository.fetchHouseProfit();
    }

    @Override
    public Long retrieveNumberOfGamesPlayedByUser(String userId) {
        return gameRepository.fetchNumberOfGamesPlayedByUser(userId);
    }

    @Override
    public Long retrieveNumberOfGamesWithGameSateOfUser(String userId, GameState gameState) {
        return gameRepository.fetchNumberOfGamesWithGameStateOfUser(userId, gameState);
    }

    @Override
    public Double retrieveTotalBetByUser(String userId) {
        return gameRepository.fetchTotalBetByUser(userId);
    }

    @Override
    public Double retrieveNetResultByUser(String userId) {
        return gameRepository.fetchNetResultByUser(userId);
    }

    @Override
    public Game saveGame(Game game) {
        try {
            GameJpaEntity entity = gameMapper.mapToJpaEntity(game, cardRepository);
            GameJpaEntity savedGameJpa = gameRepository.saveAndFlush(entity); // Ensure ID is generated

            if (savedGameJpa.getId() == null) {
                throw new RuntimeException("Failure while saving game: gameId is null");
            }
            return gameMapper.mapToDomainEntity(savedGameJpa);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveCardDraw(Long gameId, Card card, HandType holder) {
        CardJpaEntity cardEntity = cardRepository.findBySuitAndRank(card.suit().name().toUpperCase(), card.rank().name().toUpperCase())
                .orElseThrow(() -> new EntityNotFoundException("Card not found: " + card));

        GameJpaEntity gameJpaEntity = gameRepository.findById(gameId).orElseThrow(() -> new EntityNotFoundException("Game not found with ID: " + gameId));

        DrawnCardJpaEntity drawnCard = new DrawnCardJpaEntity(gameJpaEntity, cardEntity, holder);

        drawnCardsRepository.saveAndFlush(drawnCard);
        entityManager.flush();
        entityManager.clear();
    }

    @Override
    public void updateGameState(Long gameId, GameState gameState) {
        GameJpaEntity game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found with ID: " + gameId));

        game.setGameState(gameState);

        gameRepository.save(game);
        entityManager.flush();
        entityManager.clear();
    }
}
