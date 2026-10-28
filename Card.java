public class Card {

    private CardLocation location;
    private Suit suit;
    private Rank rank;
    private boolean isJoker;
    private int value;
    
    public Card(Suit s, Rank r, boolean joker, int val) {
        rank = r;
        suit = s;
        isJoker = joker;
        value = val;
        location = CardLocation.STOCK;
    }
    public void moveCard(CardLocation newLocation) {
        location = newLocation;
    }
    public CardLocation getLocation(){
        return location;
    }
    public int getCardValue(){
        return value;
    }
}
