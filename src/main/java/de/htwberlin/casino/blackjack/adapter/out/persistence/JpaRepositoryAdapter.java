package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.model.*;
import de.htwberlin.casino.blackjack.application.port.out.LoadGamePort;
import de.htwberlin.casino.blackjack.application.port.out.LoadRulesPort;
import de.htwberlin.casino.blackjack.application.port.out.LoadStatsPort;
import de.htwberlin.casino.blackjack.application.port.out.ModifyGameStatePort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
class JpaRepositoryAdapter implements LoadRulesPort, LoadStatsPort, LoadGamePort, ModifyGameStatePort {

    private final JpaGameRepository gameRepository;
    private final JpaRulesRepository rulesRepository;
    private final JpaDrawnCardsRepository drawnCardsRepository;
    private final GameMapper gameMapper;
    private final RulesMapper rulesMapper;

    @Override
    public Rules retrieveRules(RuleOption option) {
        RulesJpaEntity rulesJpaEntity = rulesRepository.findById(option.toString()).orElseThrow(EntityNotFoundException::new);
        return rulesMapper.mapToDomainEntity(RuleOption.valueOf(rulesJpaEntity.getOption()), rulesJpaEntity.getRules());
    }

    @Override
    public Game retrieveGame(Long gameId) {
        GameJpaEntity gameJpaEntity = gameRepository.findById(gameId).orElseThrow(EntityNotFoundException::new);
        recreateCardDeck(gameJpaEntity);
        return gameMapper.mapToDomainEntity(gameJpaEntity);
    }

    private void recreateCardDeck(GameJpaEntity gameJpaEntity) {
        CardDeck cardDeck = CardDeck.getInstance();

        List<DrawnCardJpaEntity> drawnCardsJpa = drawnCardsRepository.findByGameId(gameJpaEntity);

        List<Card> drawnCards = drawnCardsJpa.stream()
                .map(drawnCard -> {
                    CardJpaEntity cardJpa = drawnCard.getCardId();
                    return new Card(Rank.valueOf(cardJpa.getRank()), Suit.valueOf(cardJpa.getSuit()));
                })
                .toList();

        cardDeck.removeDealtCards(drawnCards);
    }

}
