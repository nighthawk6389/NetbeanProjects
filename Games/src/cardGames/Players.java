/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cardGames;

/**
 *
 * @author Mavis Beacon
 */
public class Players extends AbstractPlayer {
    Cards hand;
    boolean turn;
    String id;
    int money;
    int bet;

    public Players(){
        hand=new Cards(0);
        turn=false;
        id="Player 1";
        money=100;
        bet=10;
    }
    public Players(Cards hand, boolean turn, String id, int money){
        this.hand=hand;
        this.turn=turn;
        this.id=id;
        this.money=money;
        bet=10;
    }
    public Players(Cards hand){
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
    public void setId(String id){
        this.id=id;
    }
    public void setMoney(int money){
        this.money=money;
    }
    public void setBet(int bet){
        this.bet=bet;
    }
    public String getId(){
        return this.id;
    }
    public boolean getTurn(){
        return this.turn;
    }
    public int getMoney(){
        return this.money;
    }
    public int getBet(){
        return this.bet;
    }
}
