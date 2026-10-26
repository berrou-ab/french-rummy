import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
    private List<Card> deck = new ArrayList<>();

    Suit[] suits = Suit.values();
    Rank[] ranks = Rank.values();
    private int drawnCardsCount = 0;
    private int topCardIndex = -1;

    public Deck() {
        for(int i=0; i<4; i++){
            for(int j=0; j<13; j++){
                deck.add(new Card(suits[i], ranks[j], false));
            }
        }
        deck.add(new Card(null,null,true)); // 1st joker
        deck.add(new Card(null,null,true)); // 2nd joker
    }
    public void shuffle(){  // At the start of the game
        Collections.shuffle(deck);
    }
    public boolean drawCard(CardLocation player) {
        if (drawnCardsCount > 105) reShuffle();
        if (drawnCardsCount > 105){
            deck.get(drawnCardsCount++).moveCard(player);
            return true;
        }
        else {
            return false;
        }
    }
    public void discardCard(int index){
        deck.get(index).moveCard(CardLocation.DISCARD);
        topCardIndex = index;
    }
    public void upTurning() {
        if (topCardIndex == -1){
            discardCard(0);
        }
    }

    // TBD
    public boolean melding(int... cards){ 
        // 51 pts minimum for first meld. Verifications to be done in class Players
        return false;
    }
    public boolean layingOff(int card){
        return false;
    }
    public void reShuffle(){ // If the stock has been depleted and the game is still going on
        // 0. the last discarded card should be left in the discard
        // 1. recalculate drawnCardsCount
        // 2. recalculate topCardIndex
        
    }
}
