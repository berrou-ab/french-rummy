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
    private List<List<Card>> table = new ArrayList<>();
    private List<List<Integer>> tableDescription = new ArrayList<>(); // Check the method melding()

    public Deck() {
        for(int i=0; i<4; i++){
            for(int j=0; j<13; j++){
                deck.add(new Card(suits[i], ranks[j], false, ranks[j].getValue()));
            }
        }
        deck.add(new Card(null,null,true,0)); // 1st joker
        deck.add(new Card(null,null,true,0)); // 2nd joker
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
    public boolean melding(Card... cards){ 
        // 51 pts minimum for first meld. Verifications to be done in class Players
        boolean sameRank = true;
        boolean sameSuit = true;
        boolean valid = true;
        Suit currentSuit = null;
        Rank currentRank = null;
        List<Integer> ranks = new ArrayList<>();

        while(sameRank || sameSuit){
            for( Card c : cards){
                 if(c.isJoker) continue;
                 if(currentRank == null){
                    currentRank = c.rank;
                    currentSuit = c.suit;
                 }  
                 if(c.rank != currentRank) sameRank = false;
                 if(c.suit != currentSuit) sameSuit = false;
                 ranks.add(c.rank.getValue()); // Insert with the correct order
            }
            for (Integer r : ranks){
                if (sameRank) break;
                // Check if it is contiguous

            }
            if (sameRank && valid){
                // Add to table
            }

        }
        return false;

    }
    public boolean layingOff(int card){
        for(List<Card> meld : table){

        }
        return false;
    }
    public void reShuffle(){ // If the stock has been depleted and the game is still going on
        List<Card> newStock = new ArrayList<>();
        List<Card> notStock = new ArrayList<>();
        Card topCard = null;
        int notStockCounter=0;

        for(int i=0; i<106;i++){
            if(deck.get(i).getLocation()== CardLocation.DISCARD){
                if(i != topCardIndex){
                    deck.get(i).moveCard(CardLocation.STOCK);
                    newStock.add(deck.get(i));   
                }
                else{
                    topCard = deck.get(i);
                }
            }
            else{
                notStock.add(deck.get(i));
                notStockCounter++;
            }
        }
        drawnCardsCount = notStockCounter+1; // +1 for the topCard
        topCardIndex = notStockCounter;
        deck.clear();
        deck.addAll(notStock);
        deck.add(topCard);
        deck.addAll(newStock);
        
    }
}
