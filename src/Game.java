import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    // define variables used later
    private Deck deck;
    public Player player;
    public Player dealer;
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

        // Show welcome screen
        viewer.showWelcomeScreen();
        System.out.print("Press Enter to continue...");
        names.nextLine();

        // Start the game after welcome screen interaction
        dealInitialHands();
        viewer.refresh();
        playGame();

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

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        boolean gameOn = true;

        while (gameOn) {
            dealerTurn = false;
            dealInitialHands();
            viewer.refresh();
            boolean playerBusted = false;

            while (true) {
                System.out.print("Would you like to hit or stand?: ");
                String choice = scanner.nextLine().toLowerCase();
                if (choice.equals("hit")) {
                    player.addCard(deck.deal());
                    viewer.refresh();
                    System.out.println(player);
                    if (calculateValue(player) > 21) {
                        System.out.println("You busted!");
                        viewer.showLoseScreen(); // Show the lose screen immediately when the player busts
                        viewer.setGameOver(true, false);
                        System.out.print("Press Enter to continue...");
                        scanner.nextLine();
                        return;
                    }
                } else if (choice.equals("stand")) {
                    break;
                } else {
                    System.out.println("Invalid choice. Enter only 'hit' or 'stand'.");
                }
            }

            if (!playerBusted) {
                dealerTurn();
            }
            viewer.refresh();
        }
    }

    // Add a new method to determine dealers turn
    public boolean isDealerTurn() {
        return dealerTurn;
    }
    private void dealerTurn() {
        System.out.println("\nDealer's turn:");
        dealerTurn = true;
        viewer.refresh();

        while (calculateValue(dealer) < 17) {
            System.out.println("Dealer hits.");
            dealer.addCard(deck.deal());
            viewer.refresh();
            System.out.println(dealer);
        }

        determineWinner();
    }

        private int calculateValue(Player player) {
            int points = 0;
            int aces = 0;
            for (Card card : player.getHand()) {
                points += card.getValue();
                if (card.getRank().equals("A")) {
                    aces++;
                }
            }
            while (points > 21 && aces > 0) {
                points -= 10;
                aces--;
            }
            return points;
        }

        public void determineWinner() {
            int playerPoints = calculateValue(player);
            int dealerPoints = calculateValue(dealer);

            System.out.println("\nFinal Results:");
            System.out.println(player);
            System.out.println(dealer);

            Scanner scanner = new Scanner(System.in);
            boolean playerWon;

            if ((playerPoints > dealerPoints && playerPoints <= 21) || dealerPoints > 21) {
                System.out.println("You win!");
                viewer.showWinScreen();
                playerWon = true;
            } else if (playerPoints < dealerPoints && dealerPoints <= 21) {
                System.out.println("Dealer wins!");
                viewer.showLoseScreen();
                playerWon = false;
            } else {
                System.out.println("It's a tie!");
                playerWon = false;
            }

            viewer.setGameOver(true, playerWon);

            System.out.print("Press Enter to continue...");
            scanner.nextLine();

        }
        //define some methods used in the GameViewer
    public ArrayList<Card> getDealerHand() {
        return dealer.getHand();
    }

    public ArrayList<Card> getPlayerHand() {
        return player.getHand();
    }

    // play the game
    public static void main(String[] args) {
        Game blackjackGame = new Game();
        blackjackGame.playGame();
    }
}