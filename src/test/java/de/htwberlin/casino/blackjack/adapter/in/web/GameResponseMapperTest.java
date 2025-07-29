package de.htwberlin.casino.blackjack.adapter.in.web;

import de.htwberlin.casino.blackjack.application.domain.factory.hands.HandFactory;
import de.htwberlin.casino.blackjack.application.domain.factory.hands.HandFactoryImpl;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.cards.CardDeckImpl;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Rank;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Suit;
import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameImpl;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameState;
import de.htwberlin.casino.blackjack.application.domain.model.hands.HandType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GameResponseMapperTest {

    private GameResponseMapper mapper;

    private HandFactory handFactory;

    private final Long gameId = 1L;
    private final String userId = "user1";
    private final double bet = 100.0;

    @BeforeEach
    void setUp() {
        mapper = new GameResponseMapper();
        handFactory = new HandFactoryImpl();
    }

    @Test
    void toResponse_shouldReturnCorrectResponse_whenGameIsPlaying() {
        Game game = new GameImpl(
                gameId,
                userId,
                new CardDeckImpl(),
                handFactory.create(HandType.PLAYER, List.of(new Card(Rank.TEN, Suit.CLUBS), new Card(Rank.SEVEN, Suit.DIAMONDS))),
                handFactory.create(HandType.DEALER, List.of(new Card(Rank.ACE, Suit.SPADES), new Card(Rank.KING, Suit.SPADES))),
                GameState.PLAYING,
                bet
        );

        GameResponse response = mapper.toResponse(game);

        assertThat(response.gameId()).isEqualTo(gameId);
        assertThat(response.gameState()).isEqualTo("PLAYING");
        assertThat(response.bet()).isEqualTo(bet);

        assertThat(response.playerHand()).hasSize(2);
        assertThat(response.playerHand().get(0)).isEqualTo(new CardResponse("TEN", "CLUBS"));
        assertThat(response.playerHand().get(1)).isEqualTo(new CardResponse("SEVEN", "DIAMONDS"));

        assertThat(response.dealerHand()).hasSize(1);
        assertThat(response.dealerHand().getFirst()).isEqualTo(new CardResponse("ACE", "SPADES"));
    }

    @Test
    void toResponse_shouldReturnAllDealerCards_whenGameIsFinished() {
        Game game = new GameImpl(
                gameId,
                userId,
                new CardDeckImpl(),
                handFactory.create(HandType.PLAYER, List.of(new Card(Rank.FOUR, Suit.SPADES), new Card(Rank.TWO, Suit.SPADES))),
                handFactory.create(HandType.DEALER, List.of(new Card(Rank.ACE, Suit.SPADES), new Card(Rank.KING, Suit.HEARTS))),
                GameState.LOST,
                bet
        );

        GameResponse response = mapper.toResponse(game);

        assertThat(response.gameId()).isEqualTo(gameId);
        assertThat(response.gameState()).isEqualTo("LOST");
        assertThat(response.bet()).isEqualTo(bet);

        assertThat(response.playerHand()).hasSize(2);
        assertThat(response.dealerHand()).hasSize(2);
        assertThat(response.dealerHand()).containsExactly(
                new CardResponse("ACE", "SPADES"),
                new CardResponse("KING", "HEARTS")
        );
    }
}
