package de.htwberlin.casino.blackjack;

import de.htwberlin.casino.blackjack.adapter.out.persistence.*;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Rank;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Suit;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameState;
import de.htwberlin.casino.blackjack.application.domain.model.hands.HandType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BlackjackApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlackjackApplication.class, args);
    }

    @Bean
    CommandLineRunner loadInitialData(JpaRulesRepository rulesRepo, JpaCardRepository cardRepo,
                                      JpaGameRepository gameRepo, JpaDrawnCardsRepository drawnCardsRepo) {
        return args -> {
            System.out.println(rulesRepo.count() + " rules found in database.");
            if (rulesRepo.count() == 0) {
                String generalText = """
                        # Rules
                        
                        Basic set of rules for Blackjack.
                        
                        ## Goal
                        The player tries to beat the dealer.
                        They try to get a hand value closer to 21 without going over or make the dealer bust.
                        
                        ## Basics
                        - Cards 2-10 are face value
                        - Jacks, Queens, Kings count as 10
                        - Aces can be 1 or 11 (it's player's choice)
                        
                        ## Setup
                        - 1 player, 1 dealer
                        - 52-card deck with no jokers, shuffled
                        - Starting hand:
                          - player receive two cards
                          - dealer receives on card face-up, one face-down
                        - Bets:
                          - player submits their bet
                        
                        ## Gameplay
                        - If the player starts with a hand of 21 (ace + Jack/Queen/King) they have a "blackjack"
                        - Player decides to "hit" or "stand"
                          - hit: draw another card
                            --> If player's hand exceeds 21, they bust and lose
                          - stand: keep current hand
                        
                        - Player "hits" until they "stand"
                        
                        - Dealer reveals face-down card
                             - Aces count as 11 if the dealer does not bust
                             --> If dealer's hand exceeds 21, they bust and player wins
                             --> If dealer has a blackjack, the player loses unless they have a blackjack themselves
                             - If dealer's hand < 17 - "hit"
                             - If dealer's hand >= 17 end (except one is an ace - soft 17, dealer keeps hitting)
                        
                        - Determine winner of the round:
                          - Whoever is closer to 21 wins
                          - If the player's and dealer's hand have an equal value "push"
                        
                        ## Profit Distribution
                        - If player and dealer have a blackjack, the player gets their bet back ("push")
                        - If player wins, they get paid at 1 : 1 ratio (bet 1, get one)
                        - If player loses, they lose their bet
                        - If play has a blackjack (and the dealer doesn't), they get paid at 2 : 3 ratio (1,5 times their bet)
                        
                        ## Simplification
                        - no split
                        - no doubling down (if player's initial hand is 9, 10 or 11, double bet but only hit once more)
                        - no surrendering (draw back at 50% cost when having a bad hand)
                        - no side-betting (insurance)
                        """;
                rulesRepo.save(new RulesJpaEntity("GENERAL", generalText));
                rulesRepo.save(new RulesJpaEntity("HIT", "Empty for now"));
                rulesRepo.save(new RulesJpaEntity("STAND", "Empty for now"));
                System.out.println("Rules saved to database");
            }
            System.out.println(rulesRepo.count() + " rules now in database.");

            System.out.println(cardRepo.count() + " cards found in database.");
            if (cardRepo.count() == 0) {
                List<Suit> suits = Arrays.stream(Suit.values()).toList();
                List<Rank> ranks = Arrays.stream(Rank.values()).toList();
                for (Suit suit : suits) {
                    for (Rank rank : ranks) {
                        CardJpaEntity card = new CardJpaEntity(suit, rank);
                        cardRepo.save(card);
                    }
                }
                System.out.println("Cards saved to database");
            }
            System.out.println(cardRepo.count() + " cards now in database.");

            System.out.println(gameRepo.count() + " games found in database.");
            if (gameRepo.count() == 0) {
                List<CardJpaEntity> allCards = cardRepo.findAll();
                if (allCards.size() < 3) {
                    throw new IllegalStateException("Not enough cards to create sample game");
                }
                CardJpaEntity playerCard1 = allCards.get(0);
                CardJpaEntity playerCard2 = allCards.get(1);
                CardJpaEntity dealerCard1 = allCards.get(2);
                CardJpaEntity dealerCard2 = allCards.get(3);

                GameJpaEntity game = new GameJpaEntity(
                        null,
                        "1",
                        GameState.PLAYING,
                        new ArrayList<>(),
                        50.0
                );

                GameJpaEntity savedGame = gameRepo.save(game);

                DrawnCardJpaEntity draw1 = new DrawnCardJpaEntity(game, playerCard1, HandType.PLAYER);
                DrawnCardJpaEntity draw2 = new DrawnCardJpaEntity(game, playerCard2, HandType.PLAYER);
                DrawnCardJpaEntity draw3 = new DrawnCardJpaEntity(game, dealerCard1, HandType.DEALER);
                DrawnCardJpaEntity draw4 = new DrawnCardJpaEntity(game, dealerCard2, HandType.DEALER);

                drawnCardsRepo.saveAll(List.of(draw1, draw2, draw3, draw4));

                System.out.println("Sample game created with ID: " + savedGame.getId());
            }
        };
    }
}
