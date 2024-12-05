import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;
    private int cardsLeft;

    // constructor for deck
    public Deck(String[] ranks, String[] suits, int[] values) {
        cards = new ArrayList<>();

        // make an arraylist of cards giving each a rank, suit, and value
        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                cards.add(new Card(ranks[i], suit, values[i]));
            }
        }
        cardsLeft = cards.size();
    }
    // check if there are no cards left
    public boolean isEmpty(){
        return cardsLeft == 0;
    }
    // get the card left
    public int getCardsLeft() {
        return cardsLeft;
    }

    public Card deal() {
        // if the deck isn't empty take one card away and give the card at the top
        if (isEmpty()) {
            return null;
        }
        cardsLeft --;
        return cards.get(cardsLeft);
    }

    public void shuffle(){
        // schuffle the deck
        int size = cards.size();
        for(int i = size - 1; i >= 0; i--){
            // make a random number
            int randomNum = (int) (Math.random() * i);

            // swap the card at index i with the random card then do it again with the next card
            Card temp = cards.get(i);
            cards.set(i, cards.get(randomNum));
            cards.set(randomNum, temp);
        }
        cardsLeft = size;
    }
}
