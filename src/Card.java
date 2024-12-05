public class Card {

    private String rank;
    private String suit;
    private int value;

    // make a constructor giving the ran suit and value of each card
    public Card(String rank, String suit, int value){
        this.rank = rank;
        this.suit = suit;
        this.value = value;
    }
    // use the propper getters and setters for each varriable
    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    public String toString() {
        return rank + " of " + suit;
    }
}