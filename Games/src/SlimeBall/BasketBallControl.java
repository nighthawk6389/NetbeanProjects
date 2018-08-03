/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SlimeBall;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Mavis Beacon
 */
public class BasketBallControl extends JPanel implements Runnable,ActionListener,KeyListener {
    JButton one=new JButton("1 Minute");
    JButton two=new JButton("2 Minute");
    JButton four=new JButton("4 Minute");
    JButton eight=new JButton("8 Minute");
    JPanel centerHold;

    /////////////////////////////////
    int sizeOfHoop=20;
    int distanceDown=200;
    Hoop hoop1;
    Hoop hoop2;
    //////////////////////////////////////
    int amountOfPlayers=1;
    Player[] players=new Player[amountOfPlayers];
    final int PLAYER_Y_OFFSET=25;
    final int PLAYER_X_MOVE=7;
    final int PLAYER_Y_MOVE=-30;
    //////////////////////////////////////////
    int amountOfBalls=1;
    BasketBall balls []=new BasketBall[amountOfBalls];
    final int BALL_ACCELERATION=3;
    ////////////////////////////////////////////

    int MIN_DISTANCE_WALL=10;
    private int time;
    boolean isPaused=false;
    int SPEED=30;

    public BasketBallControl(){
        this.setLayout(new BorderLayout());
        this.addKeyListener(this);
        this.setFocusable(true);
        
        centerHold=new JPanel();
        centerHold.add(one);
        centerHold.add(two);
        centerHold.add(four);
        centerHold.add(eight);
        centerHold.setBackground(new Color(0,162,221));

        one.addActionListener(this);
        two.addActionListener(this);
        four.addActionListener(this);
        eight.addActionListener(this);

        this.add(centerHold,"Center");
    }//end constructor

    public void run() {
        while(true){
            moveBalls();
            movePlayers();
            repaint();

            try {
                Thread.sleep(SPEED);
                pauseGame();
            } catch (InterruptedException ex) {
                Logger.getLogger(BasketBallControl.class.getName()).log(Level.SEVERE, null, ex);
            }//end catch
        }//end while
    }//end run

    private boolean stillTime(){
        if(time>0)
            return true;
        return false;
    }//end stillTime
    private void setTime(int time){
        this.time=time;
    }
    /*
    checkGoaltending();
    checkCollision(Point p1,Point P2);
    addScore(Player p)
     **/

    private void movePlayers(){
        for(Player p: players){
            movePlayer(p);
        }//end for
    }//end movePlayers

    private void movePlayer(Player p){
        if(checkWallCollision(p)){
            movePlayerOffWall(p);
            p.setXVelocity(0);
        }//end checkWallCollison

         if(checkFloorCollision(p)){
             //System.out.println("Player Collided with floor");
            p.setYCollision(new Point(0,this.getHeight()-PLAYER_Y_OFFSET));
        }//end checkFloorCollision

        p.move();
    }//end movePlayer

    private void moveBalls(){
        for(BasketBall ball: balls){
            moveBasketBall(ball);
        }//end for
    }//end movePlayers

    private void moveBasketBall(BasketBall ball){
        if(checkWallCollision(ball))
            ball.setXCollision(new Point(MIN_DISTANCE_WALL,0),this.MIN_DISTANCE_WALL);
        if(checkFloorCollision(ball)){
            //System.out.println("Collided with floor  yVelocity Before: "+ball.getYVelocity());
            ball.setYCollision(new Point(0,this.getHeight()-ball.size));
        }//end checkFloorCollision

        AnimatedObject player=checkBallCollisions(ball);
        if(player!=null){
            System.out.println("Ball Collided with player");
            ball.setCollisionWithPlayerVelocity(player);
        }
        ball.move();

        //System.out.println(ball.getPosition().y+" "+this.getHeight()+" YVelocity: "+ball.getYVelocity());
    }//end moveBasketBall

    private void allBallsCheckScored(){
        for(BasketBall ball:balls)
            checkIfScored(ball);
    }//end ABCS

    private boolean checkIfScored(BasketBall ball){
        if(hoop1.checkScored(ball.getPosition()))
            return true;
        if(hoop2.checkScored(ball.getPosition()))
            return true;

        return false;
    }

    private AnimatedObject checkBallCollisions(AnimatedObject b){
        for(Player player:players){
            if(checkCollision(b,player))
                return player;
        }//end for
        return null;
    }//end checkAllCollsions

    private AnimatedObject checkPlayerCollisions(AnimatedObject pl){
        for(BasketBall ball:balls){
            if(checkCollision(pl,ball))
                return ball;
        }//end for
        return null;
    }//end checkAllCollsions

    private boolean checkCollision(AnimatedObject p1,AnimatedObject p2){
        if(p1.checkCollision(p2))
            return true;
        return false;
    }//end checkCollision


    private boolean checkWallCollision(AnimatedObject p1){
        if(p1.xPos<MIN_DISTANCE_WALL || p1.xPos+p1.getSize()+MIN_DISTANCE_WALL>this.getWidth())
            return true;
        return false;
    }//end checkWallCollision

    private boolean checkFloorCollision(AnimatedObject p1){
        if(p1.yPos+p1.getYOffset()>=this.getHeight())
            return true;
        return false;
    }//end checkFloorCollision

    private void movePlayerOffWall(Player p) {
        Point current=p.getPosition();
        if(current.x<MIN_DISTANCE_WALL)
           p.setPosition(MIN_DISTANCE_WALL+1,current.y);
        else if(p.xPos+p.getSize()+MIN_DISTANCE_WALL>this.getWidth())
            p.setPosition(this.getWidth()-p.getSize()-MIN_DISTANCE_WALL-1,current.y);
        else
            System.out.println("NO VELOCITY IN BUMP OFF WALL");
    }//end movePlayerOffWall


    private void initializeGame(){
        this.remove(centerHold);
        System.out.println("removed all");

        hoop1=new Hoop(0,distanceDown);
        hoop2=new Hoop(getWidth()-sizeOfHoop,distanceDown);

        for(int x=0;x<players.length;x++){
            players[x]=new Player(getWidth()/4+x*50,getHeight()-PLAYER_Y_OFFSET);
            players[x].setAcceleration(15);
            players[x].setYOffset(this.PLAYER_Y_OFFSET);
        }

        int intialYVelocity=1;
        for(int x=0;x<balls.length;x++){
            balls[x]=new BasketBall(getWidth()/4+35+x*10,getHeight()-300);
            balls[x].setYVelocity(intialYVelocity);
            balls[x].setAcceleration(this.BALL_ACCELERATION);
            balls[x].setColor(Color.red);
            balls[x].setYOffset(balls[x].size);
            balls[x].setXVelocity(5);
        }
  
        
        this.repaint();
        new Thread(this).start();
    }//end initializeGame


    @Override
    protected void paintComponent(Graphics g){
        //System.out.println("Painting");
        super.paintComponent(g);

        this.setBackground(new Color(0,162,221));
        int width=(int)this.getWidth();
        int height=(int)this.getHeight();

        //DRAW BASKETS//////////////////////////////////////////////////
        hoop1.setPoint(0,distanceDown);
        hoop2.setPoint(getWidth()-sizeOfHoop,distanceDown);
        g.drawOval(0, distanceDown, sizeOfHoop, sizeOfHoop);
        g.drawOval(width-sizeOfHoop,distanceDown,sizeOfHoop,sizeOfHoop);
        //////////////////////////////////////////////////////////////////

        
        //DRAW PLAYERS///////////////////////////////////////////////////////
        int startAngle=0,endAngle=0;
        for(Player p:players){
            g.setColor(p.color);
            if(p.getShape().equals("Semi")){
                startAngle=0;
                endAngle=180;
            }
            g.fillArc(p.xPos, p.yPos, p.getSize(), p.getSize(),startAngle, endAngle);
            g.setColor(Color.white);
            g.drawRect(p.xPos, p.yPos,p.getSize(),p.getSize());
            g.setColor(Color.black);
        }//end for players
        ///////////////////////////////////////////////////////////////////////

        //DRAW BASKETBALL////////////////////////////////////////////////////
        for(BasketBall ball:balls){
            g.setColor(ball.color);
            g.fillOval(ball.xPos, ball.yPos, ball.SIZEBALL, ball.SIZEBALL);
            g.setColor(Color.white);
            g.drawRect(ball.xPos, ball.yPos,ball.SIZEBALL,ball.SIZEBALL);
            g.setColor(Color.black);
        }//end for
        //////////////////////////////////////////////////////////

    }//end paintComponent
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==one)
            setTime(1);
        else if(e.getSource()==two)
            setTime(2);
        else if(e.getSource()==four)
            setTime(4);
        else if(e.getSource()==eight)
            setTime(8);
        initializeGame();
    }

    public void keyTyped(KeyEvent e) {

    }//end KT

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE)
            isPaused=true;
        else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
            players[0].setXVelocity(PLAYER_X_MOVE);
        else if(e.getKeyCode()==KeyEvent.VK_LEFT)
            players[0].setXVelocity(-PLAYER_X_MOVE);
        else if(e.getKeyCode()==KeyEvent.VK_UP)
            players[0].setYMoving(PLAYER_Y_MOVE);
        else if(e.getKeyCode()==KeyEvent.VK_ENTER){
            isPaused=false;
            resumeGame();
        }//end else if
        else if(e.getKeyCode()==KeyEvent.VK_EQUALS)
            SPEED=100;
    }//end KP

    public void keyReleased(KeyEvent e) {

    }//end KR

    private synchronized void pauseGame() {
        try {
            while(isPaused)
                wait();
        } catch (InterruptedException ex) {
            Logger.getLogger(BasketBallControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private synchronized void resumeGame() {
        System.out.println("resuming");
        this.notify();
    }

}