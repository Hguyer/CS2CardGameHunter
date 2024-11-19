import java.util.ArrayList;

public class Deck {
    private ArrayList<> cards;
    private int cardsLeft;

    public Deck(String[] ranks, String[] suits, int[] values) {
        cards = new ArrayList<>();

        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                cards.add(new Card(ranks[i], suit, values[i]));
            }
        }
        cardsLeft = cards.size();
    }
    public boolean isEmpty(){
        return cardsLeft == 0;
    }
    public int getCardsLeft() {
        return cardsLeft;
    }
}
