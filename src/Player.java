import java.util.ArrayList;
public class Player {

    private String name;
    private ArrayList<Card> hand;

    // give a default constructor of 100000 dollars if they obly give a name
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    // if they specify a hand give them their hand
    public Player(String name, ArrayList<Card> hand) {
        this.name = name;
        this.hand = hand;
    }
    // Getter method to get the name
    public String getName() {
        return name;
    }

    // getter method to get the player's hand
    public ArrayList<Card> getHand() {
        return hand;
    }


    // method to add a card to the player's hand
    public void addCard(Card card) {
        this.hand.add(card);
    }

    public String toString() {
        // toString to display their hand and value, use the same aces function to determine ace value
        int handValue = 0;
        int aces = 0;
        for (Card card : hand) {
            handValue += card.getValue();
            if (card.getRank().equals("A")) {
                aces++;
            }
        }
        while (handValue > 21 && aces > 0) {
            handValue -= 10;
            aces--;
        }
        return name + "'s cards: " + hand + " which is equal to " + handValue;
    }
}