package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.model.Game;
import de.htwberlin.casino.blackjack.application.domain.model.RuleOption;
import de.htwberlin.casino.blackjack.application.domain.model.Rules;
import de.htwberlin.casino.blackjack.application.port.out.LoadGamePort;
import de.htwberlin.casino.blackjack.application.port.out.LoadRulesPort;
import de.htwberlin.casino.blackjack.application.port.out.LoadStatsPort;
import de.htwberlin.casino.blackjack.application.port.out.ModifyGameStatePort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
class JpaRepositoryAdapter implements LoadRulesPort, LoadStatsPort, LoadGamePort, ModifyGameStatePort {

    private final JpaGameRepository gameRepository;
    private final JpaRulesRepository rulesRepository;
    private final GameMapper gameMapper;
    private final RulesMapper rulesMapper;

    @Override
    public Rules retrieveRules(RuleOption option) {
        RulesJpaEntity rulesJpaEntity = rulesRepository.findById(option.toString()).orElseThrow(EntityNotFoundException::new);
        return rulesMapper.mapToDomainEntity(RuleOption.valueOf(rulesJpaEntity.getOption()), rulesJpaEntity.getRules());
    }

    @Override
    public Game retrieveGame(int gameId) {
        return null;
    }
}
