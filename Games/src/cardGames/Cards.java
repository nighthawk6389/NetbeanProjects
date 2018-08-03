/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cardGames;

import java.util.*;

/**
 *
 * @author Mavis Beacon
 */
public class Cards {
    List<Card> cards;
    String [] types;

    public static final String hearts="H";
    public static final String spades="S";
    public static final String clubs="C";
    public static final String diamonds="D";

    private final int amountPerType=14;
    private int index=0;

    public Cards(){
        types=new String[4];
        types[0]=hearts;
        types[1]=spades;
        types[2]=clubs;
        types[3]=diamonds;
//        types[4]=diamonds;

        cards=new ArrayList<Card>();
        for(int x=0;x<types.length;x++){
            for(int y=1;y<amountPerType;y++){
                cards.add(new Card(y,types[x]));
            } //end BottomFor
        }//end topFor
    }//end cards

    public Cards(List cards){

        types=new String[4];
        types[0]=hearts;
        types[1]=spades;
        types[2]=clubs;
        types[3]=diamonds;
      //  types[4]=diamonds;

        this.cards=cards;
    }

    public Cards(int amountInCards){
        types=new String[4];
        types[0]=hearts;
        types[1]=spades;
        types[2]=clubs;
        types[3]=diamonds;
//        types[4]=diamonds;

        cards=new ArrayList<Card>();
        for(int x=0;x<amountInCards;x++){
            cards.add(new Card(x+1,types[x/13]));
        }//end topFor
    }

    public List getCards(){
        return this.cards;
    }
    public void setCards(List cards){
        this.cards=cards;
    }
    public String getCardType(Card card){
        return card.getType();
    }
    
    public Card getNextCard(){
        if(cards.listIterator(index).hasNext())
            return cards.listIterator(index++).next();
        else
            return null;
    }
    
    public Card getNextCard(int x){
        index=0;
        if(cards.listIterator(index).hasNext())
            return cards.listIterator(index++).next();
        else
            return null;
    }

    public void setIndex(int index){
        this.index=index;
    }

    public boolean hasNextCard(){
        if(cards.listIterator(index).hasNext())
            return true;
        else
            return false;
    }//end hasNExtCard

    public Card getCardAt(int x){
        return cards.listIterator(x).next();
    }

    public int getAceLowSum(){
        int sum=0;
        Card card;
        for(int x=0;x<cards.size();x++){
            card=(cards.get(x));
            sum+=card.getNumber();
        }
        return sum;
    }

    public int getAceHighSum(){
        int sum=0;
        Card card;
        for(int x=0;x<cards.size();x++){
            card=(cards.get(x));
            if(card.getNumber()==1)
                sum+=11;
            else
                sum+=card.getNumber();
        }
        return sum;
    }

    public int getBlackjackSum(){
        int sum=0;
        int number=0;
        int aces=0;
        for(int x=0;x<cards.size();x++){
            number=cards.get(x).getNumber();
            if(number==1)
                aces+=1;
            else
                sum+=(number>=10?10:number);
        }//end for

         if(aces>0 && sum<21){
               for(int x=0;x<aces;x++){
                   if(sum+11<=21)
                       sum+=11;
                   else
                       sum+=1;
               }//end for
            }//end if
        return sum;
    }//end getBlackjackSum

    public void empty(){
        this.cards=new ArrayList<Card>();
    }
    public void shuffle(){
        Collections.shuffle(cards);
    }
    public void setVisibility(boolean visible){
        for(Card c: cards){
            c.setVisible(visible);
        }
    }

    public String toString(){
        return cards.toString();
    }
}
