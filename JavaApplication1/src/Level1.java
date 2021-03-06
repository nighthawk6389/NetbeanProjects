import Pacman.FlashingLabel;
import Pacman.AI;
import java.awt.*;
import javax.swing.*; 
/*
 * Level1.java
 *
 * Created on May 21, 2008, 6:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class Level1 {
    Point ballsTop [];   
    Point ballsBot [];
    Point ballsRight [];
    Point ballsLeft [];
    Point ballsMidT [];
    Point ballsMidB [];
    Point limits1[];
    Point limits2 [];
    Point aiPoints [];
    Point transPort[];
    AI ai [];
    Point pacMan;
    Point test;
    Point dir[];
    Color color [];
    int smallSide;
    int sideBox;
    int sideYbox;
    int smallXturn;
    int x;
    int y;
    int origin;
    int directionX;
    int directionY;
    int oldX;
    int oldY;
    int speed;
    int amountDir;
    int timePac;
    int changePacColor;
    int allDone;
    int score;
    int numOfAI;
    boolean start =true;
    boolean suspend=false;
    boolean crazyPac=false;
    boolean gameOver=false;
    FlashingLabel label;
    
    /** Creates a new instance of Level1 */
    public Level1() {
    x=getWidth();
    y=getHeight();
    smallSide=40;
    sideBox=130;
    sideYbox=70;
    smallXturn=30;
    directionX=10;
    directionY=0;
    oldX=10;
    oldY=0;
    speed=80;
    amountDir=0;
    timePac=0;
    changePacColor=0;
    allDone=0;
    score=0;
    numOfAI=3;
    color= new Color[3];
    dir= new Point[4];
    aiPoints= new Point[(sideBox/10)*2+9];
    test= new Point(1,1);
    pacMan= new Point(x/2,(y-10)-(origin/2)-10);
          System.out.println(x+" x y "+y);
    }
    
     public void initiate(){
    ballsTop=new Point[x/10-2];   
    ballsBot=new Point[x/10-2];
    ballsRight= new Point[y/10-5];
    ballsLeft=new Point[y/10-5];
    ballsMidT= new Point[x/10-10];
    ballsMidB= new Point[x/10-10];
    aiPoints= new Point[(sideBox/10)*2+9];
    transPort= new Point[4];
    ai= new AI[numOfAI];
    limits1=new Point[ballsTop.length+ballsLeft.length+ballsMidT.length+aiPoints.length+transPort.length/2];
    limits2= new Point[ballsBot.length+ballsRight.length+ballsMidB.length+aiPoints.length+transPort.length/2];
        start=false;
        int z=0;
        int limit=0;
        System.out.println("initiate");
        
        //balls
        for(int num=15;num<x-15;num+=10){
            //System.out.println(z+"initiate");
              ballsTop[z]=    new Point(num,smallSide/2+1);
              limits1[limit]= new Point(num,smallSide/2+1);
              ballsBot[z]=    new Point(num,y-(smallSide/2)-5);
              limits2[limit]= new Point(num,y-(smallSide/2)-5);
              z++;
              limit++;
        }
        z=0;
        for(int num=smallSide-10;num<y-smallSide+10;num+=10){
            ballsLeft[z]=     new Point(smallXturn+((((x/2)-sideBox)-smallXturn)/2)-3,num+1);
            limits1[limit]=   new Point(smallXturn+((((x/2)-sideBox)-smallXturn)/2)-3,num+1);
            ballsRight[z]=    new Point((x-smallXturn)-(((x-smallXturn)-((x/2)+sideBox))/2)+1,num+1);
            limits2[limit]=   new Point((x-smallXturn)-(((x-smallXturn)-((x/2)+sideBox))/2)+1,num+1);
            z++;
            limit++;
        }
        z=0;
        for(int num=smallXturn+((((x/2)-sideBox)-smallXturn)/2)-3+10;num<=(x-smallXturn)-(((x-smallXturn)-((x/2)+sideBox))/2)+1-10;num+=10){
            ballsMidT[z]=   new Point(num,(smallSide+sideYbox)+((y/2-(sideYbox/2))-((smallSide+sideYbox)))/2+2);
            limits1[limit]= new Point(num,(smallSide+sideYbox)+((y/2-(sideYbox/2))-((smallSide+sideYbox)))/2+2);
            ballsMidB[z]= new Point (num,(y/2)+(sideYbox/2)-((y/2+(sideYbox/2))-(y-smallSide-sideYbox))/2+4);
            limits2[limit]= new Point(num,(y/2)+(sideYbox/2)-((y/2+(sideYbox/2))-(y-smallSide-sideYbox))/2+4);
            z++;
            limit++;
        }
        z=0;
        for(int num=x/2-sideBox-1+10;num<x/2+sideBox-10;num+=10){
            aiPoints[z]= new Point(num,y/2-2);
            limits1[limit]= new Point(num,y/2-2);
            limits2[limit]= new Point(num,y/2-2);
            z++;
            limit++;
        }
        for(int num=y/2-(sideYbox/2)+3-10;num<y/2+(sideYbox/2)+20;num+=10){
           // System.out.println(x/2);
            aiPoints[z]= new Point(x/2-1,num);
            limits1[limit]= new Point(x/2-1,num);
            limits2[limit]= new Point(x/2-1,num);
            z++;
            limit++;
        } 
       //transPort balls
       transPort[0]= new Point(smallXturn+((((x/2)-sideBox)-smallXturn)/2)-3-10,aiPoints[0].y-10);
       transPort[1]= new Point(smallXturn+((((x/2)-sideBox)-smallXturn)/2)-3-20,aiPoints[0].y-10);
       transPort[2]= new Point((x-smallXturn)-(((x-smallXturn)-((x/2)+sideBox))/2)+1+10,aiPoints[0].y-10);
       transPort[3]= new Point((x-smallXturn)-(((x-smallXturn)-((x/2)+sideBox))/2)+1+20,aiPoints[0].y-10);
       limits1[limit]= new Point(transPort[0]);
       limits2[limit]= new Point(transPort[2]);
       limit++;
       limits1[limit]= new Point(transPort[1]);
       limits2[limit]= new Point(transPort[2]);
       limit++;
       System.out.println(transPort[1].x+" transort 1 "+transPort[1].y);
        //pacman
        System.out.println(ballsMidB[0].x);
        pacMan= new Point(ballsBot[((x-30)/10)/2].x,(y-(smallSide/2)-5));     
        
        //AI
        for(int num=0;num<ai.length;num++){
            System.out.println(num+"ai");
            ai[num]= new AI(aiPoints[num].x,aiPoints[num].y);
        }
        
        //ability
        for(int num=0;num<dir.length;num++){
            dir[num]= new Point(0,0);
        }
        
        //colors
            color[0]= new Color(250,0,0);
            color[1]= new Color(0,250,0);
            color[2]= new Color(0,0,250);
            
            System.out.println(limits1.length+limits2.length-ai.length-ai.length+" LENGTH OF SUPPOSED END jko");
     }
     
        protected void paintComponent(Graphics g){
        super.paintComponent(g); 
            x=getWidth();
            y=getHeight();
            smallSide=40;
            smallXturn=30;
            sideBox=130;
            sideYbox=70;
            origin= (y-10)-(y-smallSide);
         //System.out.println(x+" "+y+" "+smallSide);
        
        if(start){
            initiate();
            new Thread(this).start();
        }
        //System.out.println("paint line");
        g.setColor(Color.BLUE);
        //two horizontal top bottom lines
        g.drawLine(10,10,x-10,10);
        g.drawLine(10,y-10,x-10,y-10);
        
        //four small vertical outer lines
        g.drawLine(10,10,10,smallSide);
        g.drawLine(x-10,10,x-10,smallSide);
        g.drawLine(10,y-10,10,y-smallSide);
        g.drawLine(x-10,y-10,x-10,y-smallSide);
        
        //four small horizontal outer to inner lines
        g.drawLine(10,smallSide,smallXturn,smallSide);
        g.drawLine(x-10,smallSide,x-smallXturn,smallSide);
        g.drawLine(10,y-smallSide,smallXturn,y-smallSide);
        g.drawLine(x-10,y-smallSide,x-smallXturn,y-smallSide);
        
        //four inner boundary vertical lines
        g.drawLine(smallXturn,smallSide,smallXturn,(y-smallSide)/2-10);
        g.drawLine(x-smallXturn,smallSide,x-smallXturn,(y-smallSide)/2-10);
        g.drawLine(smallXturn,y-smallSide,smallXturn,y/2+10);
        g.drawLine(x-smallXturn,y-smallSide,x-smallXturn,y/2+10);
        
        //top inner box
        g.drawLine((x/2)-sideBox,smallSide,(x/2)+sideBox,smallSide);
        g.drawLine((x/2)-sideBox,smallSide,(x/2)-sideBox,smallSide+sideYbox);
        g.drawLine((x/2)+sideBox,smallSide,(x/2)+sideBox,smallSide+sideYbox);
        g.drawLine(x/2-sideBox,smallSide+sideYbox,x/2+sideBox,smallSide+sideYbox);
        
        //lower inner box
        g.drawLine(x/2-sideBox,y-smallSide,x/2+sideBox,y-smallSide);
        g.drawLine(x/2+sideBox,y-smallSide,x/2+sideBox,y-smallSide-sideYbox); 
        g.drawLine(x/2-sideBox,y-smallSide,x/2-sideBox,y-smallSide-sideYbox);
        g.drawLine(x/2+sideBox,y-smallSide-sideYbox,x/2-sideBox,y-smallSide-sideYbox); 
        
        //middle inner box
        g.drawLine(x/2-sideBox,y/2-(sideYbox/2),x/2-sideBox,y/2+(sideYbox/2));
        g.drawLine(x/2+sideBox,y/2-(sideYbox/2),x/2+sideBox,y/2+(sideYbox/2));
        g.drawLine(x/2-sideBox,y/2-(sideYbox/2),x/2-20,y/2-(sideYbox/2));
        g.drawLine(x/2+sideBox,y/2-(sideYbox/2),x/2+20,y/2-(sideYbox/2)); 
        g.drawLine(x/2-sideBox,y/2+(sideYbox/2),x/2-20,y/2+(sideYbox/2));
        g.drawLine(x/2+sideBox,y/2+(sideYbox/2),x/2+20,y/2+(sideYbox/2));
        
        
        //balls
        g.setColor(Color.GREEN);
        for(int num=0;num<ballsTop.length;num++){
            g.fillOval(ballsTop[num].x,ballsTop[num].y,5,5);
            g.fillOval(ballsBot[num].x,ballsBot[num].y,5,5);
        }
        for(int num=0;num<ballsRight.length;num++){
            //System.out.println("num "+num);
           g.fillOval(ballsRight[num].x,ballsRight[num].y,5,5);
           g.fillOval(ballsLeft[num].x,ballsLeft[num].y,5,5); 
        }
        for(int num=0;num<ballsMidT.length;num++){
            g.fillOval(ballsMidT[num].x,ballsMidT[num].y,5,5);
            g.fillOval(ballsMidB[num].x,ballsMidB[num].y,5,5);
        }
        g.setColor(Color.BLUE);
        for(int num=0;num<aiPoints.length;num++){
            g.fillOval(aiPoints[num].x,aiPoints[num].y,5,5);
        }
        //transport balls
        g.fillOval(transPort[0].x,transPort[0].y,5,5);
        g.fillOval(transPort[1].x,transPort[1].y,5,5);
        g.fillOval(transPort[2].x,transPort[2].y,5,5);
        g.fillOval(transPort[3].x,transPort[3].y,5,5);
        //bigBalls
        g.setColor(Color.ORANGE);
        g.fillOval(ballsTop[0].x,ballsTop[0].y,10,10);
        g.fillOval(ballsTop[ballsTop.length-1].x,ballsTop[ballsTop.length-1].y,10,10);
        g.fillOval(ballsBot[0].x,ballsBot[0].y,10,10);
        g.fillOval(ballsBot[ballsBot.length-1].x,ballsBot[ballsBot.length-1].y,10,10);
        
        //pacman
        if(!crazyPac)
            g.setColor(Color.RED);
        else{
           // System.out.println(timePac+" TIMEPAC!!!!!!");
            timePac++;
            g.setColor(color[changePacColor++]);
            if(timePac>50)
                crazyPac=false;
            if(changePacColor>=3)
                changePacColor=0;
        }
        System.out.println(pacMan.x+" pacX pacY "+pacMan.y);
        g.fillOval(pacMan.x-5,pacMan.y-5,15,15);
        g.fillOval(pacMan.x,pacMan.y,1,1);  
        
        //AI
        g.setColor(Color.MAGENTA);
        for(int num=0;num<ai.length;num++){
            System.out.println(ai[num].x+" AI x AI y "+ai[num].y);
            g.fillOval(ai[num].x,ai[num].y,10,10);
            //g.drawString(num+" ",ai[num].x,ai[num].y);
        }
        if(allDone==194){
            allDone=0;
            g.drawString("You Win!!!!!",getWidth()/2-8,smallSide+(sideYbox/2));
            numOfAI++;
            label =new FlashingLabel();
            this.add(label,BorderLayout.CENTER);
            
        }
        else if(gameOver)
            g.drawString("Play Again? Press Enter",getWidth()/2-30,smallSide+(sideYbox/2));
         else
        g.drawString("Score: "+score,getWidth()/2-8,smallSide+(sideYbox/2));
        //System.out.println("allDone "+allDone);
    }
}
