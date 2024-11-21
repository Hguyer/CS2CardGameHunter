import java.util.Scanner;
import java.util.ArrayList;
public class Game {
    private Deck deck;
    private Player player;
    private Player dealer;

    public Game() {
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

        deck = new Deck(ranks, suits, values);
        deck.shuffle();

        Scanner names = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String playerName = names.nextLine();

        player = new Player("Player");

        dealer = new Player("Dealer");

        player.addCard(deck.deal());

        player.addCard(deck.deal());

        dealer.addCard(deck.deal());

        dealer.addCard(deck.deal());
    }
    public static void printInstructions(){
        System.out.println("This is your typical BlackjackGame, 1v1 you vs the house!");
        System.out.println("You will start with two cards in your hand, the dealer only shows you 1!");
        System.out.println("Your goal is to get to a value of 21, J,Q,K are all worth 10, A is 1 or 11!");
        System.out.println("You can either grab another card from the deck by hitting, or finalize your value by staying!");
        System.out.println("That's all from me. Can you beat the house? Can you win BlackJack? We'll see ....");
    }

    public void playGame(){
        printInstructions();
        Scanner hitStand = new Scanner(System.in);

        boolean playerBusted = false;
        System.out.println(player);
        System.out.println("Dealer's cards: " + dealer.getHand().get(0) + ", hidden card");

        while (true) {
            System.out.print("Would you like to hit or stand?: ");
            String choice = hitStand.nextLine().toLowerCase();
            if(choice.equals("hit")){
                player.addCard(deck.deal());
                System.out.println(player);
                if (calculatePoints(player) > 21) {
                    int loseChoice = math.random()
                    System.out.println("You busted Loser! So close to winning but not quite! HEHEHAWHAW! I WIN MONEY!");
                    playerBusted = true;
                    break;
                }
                else if(choice.equals("stand")){
                    break;
                }
                else{
                    System.out.println("Come on man, it's hit or stand, not that hard to spell :(");
                }
            }
        }
}