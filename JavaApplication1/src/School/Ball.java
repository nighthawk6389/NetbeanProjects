/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package School;

/**
 *
 * @author Mavis Beacon
 */
public class Ball {
    int xPos;
    int yPos;
    int xVelocity;
    int yVelocity;
    double time;
    double timeSinceLast=0;
    double timeDiff=0;
    boolean visible=false;
    int nextBall;

    public Ball(){
         xPos=200;
         yPos=10;
         xVelocity=0;
         yVelocity=0;
         time=0;
    }

     public Ball(int x, int y){
         xPos=x;
         yPos=y;
         xVelocity=0;
         yVelocity=0;
         time=0;
    }

}
