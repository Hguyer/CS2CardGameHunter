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

        player = new Player("Player");
        dealer = new Player("Dealer");
    }
}