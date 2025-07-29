package de.htwberlin.casino.blackjack.application.domain.service.playGame;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.cards.CardDeck;
import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameState;
import de.htwberlin.casino.blackjack.application.domain.model.hands.Hand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlayGameImplTest {

    private PlayGameImpl playGame;

    @BeforeEach
    void setUp() {
        playGame = new PlayGameImpl();
    }

    @Test
    void givenBlackjackHand_whenCheckInitialBlackjack_thenReturnTrue() {
        Hand hand = mock(Hand.class);
        when(hand.getCards()).thenReturn(List.of(mock(Card.class), mock(Card.class)));
        when(hand.getTotal()).thenReturn(21);

        assertTrue(playGame.handHasInitialBlackjack(hand));
    }

    @Test
    void givenNonBlackjackHand_whenCheckInitialBlackjack_thenReturnFalse() {
        Hand hand = mock(Hand.class);
        when(hand.getCards()).thenReturn(List.of(mock(Card.class), mock(Card.class)));
        when(hand.getTotal()).thenReturn(20);

        assertFalse(playGame.handHasInitialBlackjack(hand));
    }

    @Test
    void givenMoreThanTwoCards_whenCheckInitialBlackjack_thenReturnFalse() {
        Hand hand = mock(Hand.class);
        when(hand.getCards()).thenReturn(List.of(mock(Card.class), mock(Card.class), mock(Card.class)));
        when(hand.getTotal()).thenReturn(21);

        assertFalse(playGame.handHasInitialBlackjack(hand));
    }

    @Test
    void givenPlayerHandOver21_whenCheckIsPlayerBusted_thenReturnTrue() {
        Game game = mock(Game.class);
        Hand playerHand = mock(Hand.class);
        when(game.getPlayerHand()).thenReturn(playerHand);
        when(playerHand.getTotal()).thenReturn(22);

        assertTrue(playGame.isPlayerBusted(game));
    }

    @Test
    void givenPlayerHandUnder21_whenCheckIsPlayerBusted_thenReturnFalse() {
        Game game = mock(Game.class);
        Hand playerHand = mock(Hand.class);
        when(game.getPlayerHand()).thenReturn(playerHand);
        when(playerHand.getTotal()).thenReturn(18);

        assertFalse(playGame.isPlayerBusted(game));
    }

    @Test
    void givenGame_whenPlayPlayerTurn_thenDrawOneCard() {
        Game game = mock(Game.class);
        CardDeck deck = mock(CardDeck.class);
        Card card = mock(Card.class);

        when(game.getCardDeck()).thenReturn(deck);
        when(deck.drawCard()).thenReturn(card);

        Card result = playGame.playPlayerTurn(game);
        assertEquals(card, result);
    }

    @Test
    void givenDealerHandUnder17_whenPlayDealerTurn_thenDrawUntil17OrMore() {
        Game game = mock(Game.class);
        CardDeck deck = mock(CardDeck.class);
        Hand dealerHand = mock(Hand.class);

        when(game.getDealerHand()).thenReturn(dealerHand);
        when(game.getCardDeck()).thenReturn(deck);

        when(dealerHand.getTotal()).thenReturn(14, 16, 17);  // triggers two draws, then stop
        Card card1 = mock(Card.class);
        Card card2 = mock(Card.class);

        when(deck.drawCard()).thenReturn(card1, card2);

        List<Card> drawnCards = playGame.playDealerTurn(game);

        assertEquals(2, drawnCards.size());
        assertEquals(card1, drawnCards.get(0));
        assertEquals(card2, drawnCards.get(1));
    }

    @Test
    void givenPlayerBust_whenDetermineResult_thenLost() {
        Hand player = mock(Hand.class);
        Hand dealer = mock(Hand.class);
        when(player.getTotal()).thenReturn(22);
        when(dealer.getTotal()).thenReturn(17);

        assertEquals(GameState.LOST, playGame.determineResult(player, dealer));
    }

    @Test
    void givenDealerBustAndPlayerNotBust_whenDetermineResult_thenWon() {
        Hand player = mock(Hand.class);
        Hand dealer = mock(Hand.class);
        when(player.getTotal()).thenReturn(19);
        when(dealer.getTotal()).thenReturn(22);

        assertEquals(GameState.WON, playGame.determineResult(player, dealer));
    }

    @Test
    void givenPlayerHigherTotal_whenDetermineResult_thenWon() {
        Hand player = mock(Hand.class);
        Hand dealer = mock(Hand.class);
        when(player.getTotal()).thenReturn(20);
        when(dealer.getTotal()).thenReturn(18);

        assertEquals(GameState.WON, playGame.determineResult(player, dealer));
    }

    @Test
    void givenDealerHigherTotal_whenDetermineResult_thenLost() {
        Hand player = mock(Hand.class);
        Hand dealer = mock(Hand.class);
        when(player.getTotal()).thenReturn(17);
        when(dealer.getTotal()).thenReturn(20);

        assertEquals(GameState.LOST, playGame.determineResult(player, dealer));
    }

    @Test
    void givenEqualTotals_whenDetermineResult_thenPush() {
        Hand player = mock(Hand.class);
        Hand dealer = mock(Hand.class);
        when(player.getTotal()).thenReturn(18);
        when(dealer.getTotal()).thenReturn(18);

        assertEquals(GameState.PUSH, playGame.determineResult(player, dealer));
    }
}
