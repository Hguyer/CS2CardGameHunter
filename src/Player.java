import java.util.ArrayList;
public class Player {
    private String name;
    private ArrayList<Card> hand;
    private int money;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.money = 100000;
    }

    public Player(String name, ArrayList<Card> hand) {
        this.name = name;
        this.hand = hand;
        this.money = 100000;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getMoney() {
        return money;
    }

    public void updateMoney(int amount) {
        this.money += amount;
    }

    public void addCard(Card card) {
        this.hand.add(card);
    }

    public String toString() {
        return name + "'s cards: " + hand + " & has $" + money;
    }
}