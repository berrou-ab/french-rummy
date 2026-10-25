import java.util.List;
import java.util.ArrayList;

public class Deck {
    private List<Card> deck = new ArrayList<>();

    Suit[] suits = Suit.values();
    Rank[] ranks = Rank.values();
    public Deck() {
        for(int i=0; i<4; i++){
            for(int j=0; j<13; j++){
                deck.add(new Card(suits[i], ranks[j], false));
            }
        }
        deck.add(new Card(null,null,true)); // 1st joker
        deck.add(new Card(null,null,true)); // 2nd joker
    }
    public void shuffle(){
        
    }
}
