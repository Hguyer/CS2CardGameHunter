import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    // define variables used later
    private Deck deck;
    private Player player;
    private Player dealer;
    private GameViewer viewer;
    private boolean dealerTurn = false;

    public Game() {
        viewer = new GameViewer(this);
        // make the ranks suits and values, aces will be modified in calculatePoints later
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};
        // make a new deck
        deck = new Deck(ranks, suits, values);
        // shuffle it
        deck.shuffle();

        // get the players names
        Scanner names = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String playerName = names.nextLine();

        // make a new player called player and the dealer or the house
        player = new Player(playerName);

        dealer = new Player("Dealer");


        dealInitialHands();

        viewer.refresh();
    }
    private void dealInitialHands() {
        player.getHand().clear();
        dealer.getHand().clear();

        if (deck.getCardsLeft() < 4) {
            deck.shuffle();
        }

        for (int i = 0; i < 2; i++) {
            player.addCard(deck.deal());
            dealer.addCard(deck.deal());
        }
    }

    // show instructions
    public static void printInstructions() {
        System.out.println("Welcome to Blackjack! You start with $100.");
        System.out.println("In each round, you can bet a portion of your money.");
        System.out.println("You can hit to get a card or stand to finalize your hand.");
        System.out.println("J, Q, K are worth 10 points. Aces are 1 or 11.");
        System.out.println("If you bust (go over 21), you lose your bet.");
        System.out.println("If you win, you double your bet.");
        System.out.println("The game ends when you run out of money.");
        System.out.println("That's all from me. Can you beat the house? Can you win BlackJack? We'll see ....");
        System.out.println("\n\n ~~~~~~~~~~~~~~~~~~~\n\n");
    }

    public void playGame() {
        printInstructions();

        // create a new scanner to get all the inputs for the game
        Scanner gamble = new Scanner(System.in);
        // set the game on to true, if it is ever false the game is over
        boolean gameOn = true;

        // when the game isn't over and you still can bet play the game
        while (gameOn && player.getMoney() > 0) {
            // Prompt to place a bet
            System.out.println("\nYou have $" + player.getMoney());
            System.out.print("How much would you like to bet? ");
            int bet = gamble.nextInt();

            // Check if bet is valid
            if (bet <= 0 || bet > player.getMoney()) {
                System.out.println("Invalid bet. You have to bet a positive amount not greater than your current balence.");
                continue;
            }
            gamble.nextLine();
            dealInitialHands();
            viewer.refresh();
            // Player's turn
            boolean playerBusted = false;
            //infinate loop
            while (true) {
                System.out.print("Would you like to hit or stand?: ");
                // make everything a uniform lowercase to avoid capital letter mistakes
                String choice = gamble.nextLine().toLowerCase();
                // if you hit you get a card and display your deck if you stand your turn ends
                if (choice.equals("hit")) {
                    player.addCard(deck.deal());
                    viewer.refresh();
                    System.out.println(player);
                    // if you have more then 21 you bust
                    if (calculateValue(player) > 21) {
                        System.out.println("You busted!");
                        playerBusted = true;
                        break;
                    }
                } else if (choice.equals("stand")) {
                    break;
                } else {
                    System.out.println("Invalid choice. Enter only 'hit' or 'stand'.");
                }
            }

            // If player busted, they lose the bet
            if (playerBusted) {
                System.out.println("You lost the round! You lose $" + bet);
                player.updateMoney(-bet);
                dealer.updateMoney(bet);
            } else {
                dealerTurn(bet);
            }

            if (player.getMoney() <= 0) {
                System.out.println("Game over! You're broke.");
                gameOn = false;
            }
            viewer.refresh();
        }
    }
    // Add a new method to determine dealers turn
    public boolean isDealerTurn() {
        return dealerTurn;
    }
    private void dealerTurn(int bet) {
        System.out.println("\nDealer's turn:");
        //set the dealers turn to true and refresh the window
        dealerTurn = true;
        viewer.refresh();
        // hit the dealer
        while (calculateValue(dealer) < 17) {
            System.out.println("Dealer hits.");
            dealer.addCard(deck.deal());
            viewer.refresh();
            System.out.println(dealer);
        }
        // dealer busts message
        if (calculateValue(dealer) > 21) {
            System.out.println("Dealer busted! You win the round!");
            player.updateMoney(bet);
        } else {
            determineWinner(bet);
        }
    }

    private int calculateValue(Player player) {
        int points = 0;
        // create the aces problem
        int aces = 0;
        for (Card card : player.getHand()) {
            // add the value of the cards to points
            points += card.getValue();
            if (card.getRank().equals("A")) {
                aces++;
            }
        }
        // if you bust but have an ace the ace value becomes 1
        while (points > 21 && aces > 0) {
            points -= 10;
            aces--;
        }
        return points;
    }

    private void determineWinner(int bet) {
        int playerPoints = calculateValue(player);
        int dealerPoints = calculateValue(dealer);

        System.out.println("\nFinal Results:");
        System.out.println(player);
        System.out.println(dealer);

        // compeare the points if you win update your money
        if (playerPoints > dealerPoints) {
            System.out.println("You win! You gain $ " + bet);
            player.updateMoney(bet);
        } else if (playerPoints < dealerPoints) {
            System.out.println("Dealer wins! You lose $ " + bet);
            player.updateMoney(-bet);
        } else {
            System.out.println("It's a tie! No money change hands.");
        }
    }
    //define some methods used in the GameViewer
    public ArrayList<Card> getDealerHand() {
        return dealer.getHand();
    }

    public ArrayList<Card> getPlayerHand() {
        return player.getHand();
    }

    public boolean isGameOver() {
        return player.getMoney() <= 0;
    }

    public String getGameResult() {
        if(isGameOver()){
            return"Game over! You're out of money.";
        }
        return "";
    }

// play the game
public static void main(String[] args) {
    Game blackjackGame = new Game();
    blackjackGame.playGame();
}
}