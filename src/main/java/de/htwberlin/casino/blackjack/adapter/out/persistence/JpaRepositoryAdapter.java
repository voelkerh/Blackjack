package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.model.game.GameImpl;
import de.htwberlin.casino.blackjack.application.domain.model.rules.RuleOption;
import de.htwberlin.casino.blackjack.application.domain.model.rules.Rules;
import de.htwberlin.casino.blackjack.application.domain.model.stats.StatsOption;
import de.htwberlin.casino.blackjack.application.domain.service.emitStats.Stats;
import de.htwberlin.casino.blackjack.application.port.out.LoadGamePort;
import de.htwberlin.casino.blackjack.application.port.out.LoadRulesPort;
import de.htwberlin.casino.blackjack.application.port.out.LoadStatsPort;
import de.htwberlin.casino.blackjack.application.port.out.ModifyGameStatePort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Adapts Spring Data JPA repositories to domain ports.
 * Handles persistence-related operations for rules and games.
 */
@RequiredArgsConstructor
@Repository
class JpaRepositoryAdapter implements LoadRulesPort, LoadStatsPort, LoadGamePort, ModifyGameStatePort {

    private final JpaGameRepository gameRepository;
    private final JpaRulesRepository rulesRepository;
    private final JpaDrawnCardsRepository drawnCardsRepository;
    private final GameMapper gameMapper;
    private final RulesMapper rulesMapper;

    /**
     * Loads a ruleset from the database based on the given option.
     *
     * @param option the selected {@link RuleOption}
     * @return the mapped domain {@link Rules} object
     * @throws EntityNotFoundException if the ruleset is not found
     */
    @Override
    public Rules retrieveRules(RuleOption option) {
        RulesJpaEntity rulesJpaEntity = rulesRepository.findById(option.toString()).orElseThrow(EntityNotFoundException::new);
        return rulesMapper.mapToDomainEntity(RuleOption.valueOf(rulesJpaEntity.getOption()), rulesJpaEntity.getRules());
    }

    /**
     * Loads a game by its ID from the database and maps it to the domain model.
     *
     * @param gameId the ID of the game
     * @return the mapped domain {@link GameImpl} object
     * @throws EntityNotFoundException if the game is not found
     */
    @Override
    public GameImpl retrieveGame(Long gameId) {
        GameJpaEntity gameJpaEntity = gameRepository.findById(gameId).orElseThrow(EntityNotFoundException::new);
        return gameMapper.mapToDomainEntity(gameJpaEntity, drawnCardsRepository);
    }

    /**
     * Retrieves statistical data based on the specified statistics option.
     *
     * @param option the type of statistics to retrieve; determines the query executed
     * @param userId the ID of the user for whom to retrieve statistics; used only when option is USER
     * @return the requested {@link Stats} data corresponding to the option;
     * returns null if the option is null
     */
    @Override
    public Stats retrieveStats(StatsOption option, String userId) {
        return switch (option) {
            case USER -> gameRepository.fetchUserStats(userId);
            case OVERVIEW -> gameRepository.fetchOverviewStats();
            case null -> null;
        };
    }

}
