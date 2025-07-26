package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameImpl;
import de.htwberlin.casino.blackjack.application.domain.model.rules.RuleOption;
import de.htwberlin.casino.blackjack.application.domain.model.rules.Rules;
import de.htwberlin.casino.blackjack.application.domain.service.emitStats.OverviewStats;
import de.htwberlin.casino.blackjack.application.domain.service.emitStats.UserStats;
import de.htwberlin.casino.blackjack.application.port.out.LoadGamePort;
import de.htwberlin.casino.blackjack.application.port.out.LoadRulesPort;
import de.htwberlin.casino.blackjack.application.port.out.LoadStatsPort;
import de.htwberlin.casino.blackjack.application.port.out.ModifyGamePort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Adapts Spring Data JPA repositories to domain ports.
 * Handles persistence-related operations for rules, stats and games.
 */
@RequiredArgsConstructor
@Repository
class JpaRepositoryAdapter implements LoadRulesPort, LoadStatsPort, LoadGamePort, ModifyGamePort {

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
        GameJpaEntity gameJpaEntity = gameRepository.findById(gameId).orElseThrow(EntityNotFoundException::new);
        return gameMapper.mapToDomainEntity(gameJpaEntity, drawnCardsRepository);
    }

    @Override
    public UserStats retrieveUserStats(String userId) {
        return gameRepository.fetchUserStats(userId);
    }

    @Override
    public OverviewStats retrieveOverviewStats() {
        return gameRepository.fetchOverviewStats();
    }

    @Override
    public void saveGame(Game game) {
        GameJpaEntity entity = gameMapper.mapToJpaEntity(game);
        gameRepository.save(entity);
    }

    @Override
    public void saveCardDraw(Long gameId, Card card, String holder) {
        GameJpaEntity game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found with ID: " + gameId));

        CardJpaEntity cardEntity = cardRepository.findBySuitAndRank(card.suit().name(), card.rank().name())
                .orElseThrow(() -> new EntityNotFoundException("Card not found: " + card));

        DrawnCardJpaEntity drawnCard = new DrawnCardJpaEntity(game, cardEntity, holder);

        drawnCardsRepository.save(drawnCard);
    }

    @Override
    public void updateGameState(Long gameId, String state) {
        GameJpaEntity game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found with ID: " + gameId));

        game.setGameState(state);

        gameRepository.save(game);
    }
}
