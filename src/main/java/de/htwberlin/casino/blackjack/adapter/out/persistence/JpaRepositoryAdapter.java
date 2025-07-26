package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.model.*;
import de.htwberlin.casino.blackjack.application.port.out.LoadGamePort;
import de.htwberlin.casino.blackjack.application.port.out.LoadRulesPort;
import de.htwberlin.casino.blackjack.application.port.out.LoadStatsPort;
import de.htwberlin.casino.blackjack.application.port.out.ModifyGameStatePort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

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
        return gameMapper.mapToDomainEntity(gameJpaEntity.getId(), gameJpaEntity.getUserId(), getCardDeck(gameJpaEntity), getPlayerHand(gameJpaEntity), getDealerHand(gameJpaEntity), GameState.valueOf(gameJpaEntity.getGameState()), gameJpaEntity.getBet());
    }

    private CardDeck getCardDeck(GameJpaEntity gameJpaEntity) {
        CardDeck cardDeck = CardDeck.getInstance();

        List<DrawnCardJpaEntity> drawnCardsJpa = drawnCardsRepository.findByGameId(gameJpaEntity);

        List<Card> drawnCards = drawnCardsJpa.stream()
            .map(drawnCard -> {
                CardJpaEntity cardJpa = drawnCard.getCardId();
                return new Card(Rank.valueOf(cardJpa.getRank()), Suit.valueOf(cardJpa.getSuit()));
            })
            .toList();

        cardDeck.removeDealtCards(drawnCards);

        return cardDeck;
    }

    private PlayerHand getPlayerHand(GameJpaEntity gameJpaEntity) {
        List<DrawnCardJpaEntity> playerHandCards = gameJpaEntity.getPlayerHand();
        CardJpaEntity card1Jpa = playerHandCards.getFirst().getCardId();
        Card card1 = new Card(Rank.valueOf(card1Jpa.getRank()), Suit.valueOf(card1Jpa.getSuit()));
        CardJpaEntity card2Jpa = playerHandCards.get(1).getCardId();
        Card card2 = new Card(Rank.valueOf(card2Jpa.getRank()), Suit.valueOf(card2Jpa.getSuit()));
        return new PlayerHand(card1, card2);
    }

    private DealerHand getDealerHand(GameJpaEntity gameJpaEntity) {
        List<DrawnCardJpaEntity> dealerHandCard = gameJpaEntity.getDealerHand();
        CardJpaEntity cardJpa = dealerHandCard.getFirst().getCardId();
        Card card = new Card(Rank.valueOf(cardJpa.getRank()), Suit.valueOf(cardJpa.getSuit()));
        return new DealerHand(card);
    }
}
