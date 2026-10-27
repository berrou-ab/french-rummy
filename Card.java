public class Card {

    private CardLocation location;
    private Suit suit;
    private Rank rank;
    private boolean isJoker;
    
    public Card(Suit s, Rank r, boolean joker) {
        rank = r;
        suit = s;
        isJoker = joker;
        location = CardLocation.STOCK;
    }
    public void moveCard(CardLocation newLocation) {
        location = newLocation;
    }
    public CardLocation getLocation(){
        return location;
    }
}
