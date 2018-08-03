/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cardGames;

/**
 *
 * @author Mavis Beacon
 */
public class Card {
    private int number;
    private String type;
    private boolean visible;

    public Card(){
        number=2;
        type="default";
        visible=true;
    }

    public Card(int number, String type){
        this.number=number;
        this.type=type;
        this.visible=true;
    }

    public int getNumber(){
        return this.number;
    }
    public String getType(){
        return this.type;
    }
    public boolean getVisible(){
        return this.visible;
    }
    public void setVisible(boolean visible){
        this.visible=visible;
    }

    public Card getRandomCard(){
        String [] types=new String[4];
        types[0]="H";//"hearts";
        types[1]="S";//"spades";
        types[2]="C";//"clubs";
        types[3]="D";//diamonds";

        int cardRand=(int)(Math.random()*12)+1;
        int typeRand=(int)(Math.random()*3);

        return new Card(cardRand,types[typeRand]);
    }

    public String toString(){
        if(this.number==1)
          return "Ace"+this.type;
        else if(this.number==11)
            return "Jack"+this.type;
        else if(this.number==12)
            return "Queen"+this.type;
        else if(this.number==13)
            return "King"+this.type;
        else
            return this.number+""+this.type;
    }
}
