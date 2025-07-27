package de.htwberlin.casino.blackjack.application.domain.model.game;

import de.htwberlin.casino.blackjack.application.domain.model.cards.CardDeckImpl;
import de.htwberlin.casino.blackjack.application.domain.model.hands.DealerHand;
import de.htwberlin.casino.blackjack.application.domain.model.hands.PlayerHand;
import lombok.Getter;

@Getter
public class GameImpl implements Game {

    private final Long id;
    private final String userId;
    private CardDeckImpl cardDeck;
    private PlayerHand playerHand;
    private DealerHand dealerHand;
    private GameState gameState;
    private final double bet;

    public GameImpl(Long id, String userId, CardDeckImpl cardDeck,
                    PlayerHand playerHand, DealerHand dealerHand, GameState gameState, double bet) {
        this.id = id;
        this.userId = userId;
        this.cardDeck = cardDeck;
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
        this.gameState = gameState;
        this.bet = bet;
    }

    public GameImpl(Long id, String userId, double bet) {
        this.id = id;
        this.userId = userId;
        this.bet = bet;
        this.initialize();
    }

    public void initialize() {
        cardDeck = new CardDeckImpl();
        playerHand = new PlayerHand(cardDeck.drawCard(), cardDeck.drawCard());
        dealerHand = new DealerHand(cardDeck.drawCard());
        gameState = GameState.PLAYING;
    }

    @Override
    public boolean isPlayerBusted() {
        return playerHand.getTotal() > 21;
    }

    @Override
    public boolean isDealerBusted() {
        return false;
    }

}
