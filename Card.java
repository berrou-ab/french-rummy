public class Card {

    private CardLocation location;
    public final Suit suit;
    public final Rank rank;
    public final boolean isJoker;
    public final int value;
    
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
}
