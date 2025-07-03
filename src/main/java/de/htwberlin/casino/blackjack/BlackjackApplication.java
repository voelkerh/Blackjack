package de.htwberlin.casino.blackjack;

import de.htwberlin.casino.blackjack.adapter.out.persistence.JpaRulesRepository;
import de.htwberlin.casino.blackjack.adapter.out.persistence.RulesJpaEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlackjackApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlackjackApplication.class, args);
    }

    @Bean
    CommandLineRunner loadInitialData(JpaRulesRepository rulesRepo) {
        return args -> {
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
                        - no doubling down (if player's inital hand is 9, 10 or 11, double bet but only hit once more)
                        - no surrendering (draw back at 50% cost when having a bad hand)
                        - no side-betting (insurance)
                        """;
                rulesRepo.save(new RulesJpaEntity("GENERAL", generalText));
                rulesRepo.save(new RulesJpaEntity("HIT", "Empty for now"));
                rulesRepo.save(new RulesJpaEntity("STAND", "Empty for now"));
            }
        };
    }

}
