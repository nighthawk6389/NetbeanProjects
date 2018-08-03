/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cardGames;

/**
 *
 * @author Mavis Beacon
 */
public class Dealer extends AbstractPlayer{
    Cards hand;
    boolean turn;

    public Dealer(){
        hand=new Cards(0);
        turn=false;
    }
    public Dealer(Cards hand, boolean turn){
        this.hand=hand;
        this.turn=turn;
    }
    public Dealer(Cards hand){
        this.hand=hand;
        turn=false;
    }

    public Cards getHand(){
        return this.hand;
    }
    public boolean isTurn(){
        return this.turn;
    }
    public void setHand(Cards hand){
        this.hand=hand;
    }
    public void setTurn(boolean turn){
        this.turn=turn;
    }

    public int getTotalSum(){
        int sum=0;
        Card card;
        for(int x=0;x<hand.getCards().size();x++){
            card=(Card)(hand.getCards().get(x));
            sum+=card.getNumber();
        }
        return sum;
    }//end getTotalSum
}//end Dealer
