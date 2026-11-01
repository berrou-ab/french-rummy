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
    private List<List<Card>> table = new ArrayList<>(); // In each row, cards are in ascending order

    public Deck(int decksCount, int jokersCount) {
        // Decks
        for(int k=0; k< decksCount;k++){
            for(int i=0; i<4; i++){
                for(int j=0; j<13; j++)
                    deck.add(new Card(suits[i], ranks[j], false, ranks[j].getValue()));
            }
        }
        // Jokers
        for(int i=0; i< jokersCount;i++)
            deck.add(new Card(null,null,true,0));

    }
    public void shuffle(){  // At the start of the game
        Collections.shuffle(deck);
    }
    public boolean drawCard(CardLocation player) {
        if (drawnCardsCount > deck.size()-1) reShuffle();
        if (drawnCardsCount > deck.size()-1){
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
    // 51 pts minimum for first meld. Verifications to be done in class Player
    // Cards are assumed arranged in the right order going into this method
    public int melding(List<Card> cards){
        if(cards.size() < 3 || cards.size() > 13) return -1; // Minimum 3 cards. One of each rank maximum
        return meldingAux(cards);
    }

    public int meldingAux(List<Card> cards){ 
        boolean sameRank = true;
        boolean sameSuit = true;
        boolean valid = true;
        Suit currentSuit = null;
        Rank currentRank = null;
        int pointsCount = 0;
        int jokersCount = 0;

        while((sameRank || sameSuit) && valid && jokersCount < 2){
            for( Card c : cards){
                if(c.isJoker){
                    jokersCount++;
                    continue;
                }
                if(currentRank == null){
                    currentRank = c.rank;
                    currentSuit = c.suit;
                }  
                if(c.rank != currentRank) sameRank = false;
                if(c.suit != currentSuit) sameSuit = false;
            }
            // Check if it is contiguous
            int previousRank = 0;
            for (int i=0;i< cards.size();i++){
                
                // If joker is first:
                if((previousRank==0) && cards.get(i).isJoker) continue;
                // If joker is not first:
                if(cards.get(i).isJoker){
                    previousRank++;
                    pointsCount+= previousRank;
                    continue;
                }
                // First non joker card
                if(previousRank == 0){
                    previousRank = cards.get(i).value;
                    pointsCount = previousRank+previousRank-1;
                    if(!sameRank) continue;
                }
                // If the meld is a set, calculate the final pointsCount and break
                if(sameRank){
                    pointsCount = previousRank * cards.size();
                    break;
                }
                // The rest of the non joker cards
                if(cards.get(i).value == previousRank +1){
                        previousRank = cards.get(i).value;
                        pointsCount+= cards.get(i).value;
                }
                else{
                        // Ace high
                        if(!(previousRank == 10 && cards.get(i).value == 1 && i == cards.size() -1)) valid = false;
                        pointsCount+= 10; 
                }
            }
            // Adding to table to eventually be done at the main level to check points requirements for first meld
            table.add(cards); 
            return pointsCount;
            
        }
        return -1;

    }
    public boolean layingOff(Card card){
        if(card.isJoker) return false;
        for(List<Card> meld : table){
            boolean sameRank = true;
            if (!(meld.get(0).rank == meld.get(1).rank)) sameRank=false;
            
            // Check whether the card must be added at the beginning or the end
            if((card.value == meld.get(0).value -1) && !meld.get(0).isJoker){
                meld.add(0,card);
                return true;
            }
            if((card.value == meld.get(meld.size()-1).value +1) && !meld.get(meld.size()-1).isJoker){
                meld.add(card);
                return true;
            }
            // Check whether this card replaces a joker
            for(int i=0; i< meld.size()-1;i++){
                if (meld.get(i).isJoker){
                    if(i==0){
                        if(card.value == meld.get(1).value -1){
                            meld.add(0,card);
                            return true;
                        }
                    }
                    else if(i== meld.size()-1){
                        if(card.value == meld.get(i-1).value +1){
                            meld.add(i,card);
                            return true;
                        }
                    }
                    else{
                        if((card.value == meld.get(i-1).value + 1)&&(card.value == meld.get(i+1).value -1)){
                            meld.add(i,card);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public void reShuffle(){ // If the stock has been depleted and the game is still going on
        List<Card> newStock = new ArrayList<>();
        List<Card> notStock = new ArrayList<>();
        Card topCard = null;
        int notStockCounter=0;

        for(int i=0; i<deck.size();i++){
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
