/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package connect4;

/**
 *
 * @author Mavis Beacon
 */
public class Player {

    int color;
    boolean isAI;

    public Player(){
        color=1;
        isAI=false;
    }
    public Player(int color){
        this.color=color;
        isAI=false;
    }
    public Player(int color,boolean isAI){
        this.color=color;
        this.isAI=isAI;
    }

    public int notifyTurn(Board board){
        return -1;
    }

    

}
