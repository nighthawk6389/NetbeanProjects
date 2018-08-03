/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package School;

import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Mavis Beacon
 */
public class BouncingBall extends JPanel implements Runnable{
    
    int xScreen;
    int yScreen;
    //double time;
    //double timeSinceLast;
    //double timeDiff=0;
    int counter=1;
    int skip=0;
    Ball [] balls=new Ball[100];
 
    public static void main(String args []){
        JFrame frame=new JFrame();
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        BouncingBall b=new BouncingBall(200,0);

        frame.getContentPane().add(b);
    }

    public BouncingBall(){
         xScreen=this.getWidth();
         yScreen=this.getHeight();
         //time=timeSinceLast=0;

         balls=initiateBalls();
         new Thread(this).start();

    }

    public BouncingBall(int x,int y){
         xScreen=this.getWidth();
         yScreen=400;
         //time=timeSinceLast=0;
         balls=initiateBalls();
         new Thread(this).start();
    }

    public void getEquations(Ball ball){
         ball.timeDiff=ball.time-ball.timeSinceLast;

        if(ball.yPos>yScreen-10){
            //System.out.println("Time Since Last: "+timeSinceLast+" time: "+time);
            ball.yVelocity=(int)(-10*ball.time+11);//(Math.random()*15));
            //yVelocity=-27
            ball.timeSinceLast=ball.time;
            ball.yPos=yScreen-10;
            System.out.println("Changed yVelocty: "+ball.yVelocity+" yPos: "+ball.yPos+" Time: "+ball.time);
            //if(counter<balls.length && !balls[counter].visible )
                 //balls[counter++].visible=true;//Math.random()*2>1?true:false;
            System.out.println("Counter: "+counter);
        }

         if(ball.yPos>yScreen-20 && ball.yVelocity>-10){
             System.out.println("Killing velocity: "+ball.yVelocity);
             ball.yVelocity=0;
             return ;
         }

         if(ball.yPos<10){
             ball.time=0;
             ball.yVelocity=(int)(-10*ball.time+13);
             ball.yPos=10;
         }

         if(Math.abs(ball.yVelocity+(10*(ball.timeDiff)))-5<0 && ball.yVelocity<0){
            ball.time=1.5;
            ball.yVelocity=0;
         }

        if(ball.nextBall*2==skip && ball.nextBall<balls.length){
            balls[ball.nextBall].visible=true;
            counter++;
        }
        ball.yPos=(int)(ball.yPos+(ball.yVelocity*ball.time)+(5*(ball.time*ball.time)));
        ball.time=ball.time+.2;
        
    }

    @Override
    public void run() {
        while(true){
            for(Ball ball:balls){
                if(ball.visible)
                    getEquations(ball);
            }
            skip++;
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(BouncingBall.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
     protected void paintComponent(Graphics g){
        super.paintComponent(g);

        yScreen=this.getHeight();

        //System.out.println(yPos+" Time "+time);
        for(Ball ball:balls){
            //if(ball.visible)
                g.fillOval(ball.xPos, ball.yPos, 15, 15);
        }
    }

    private Ball[] initiateBalls(){
        Ball [] temp=new Ball[balls.length];

        for(int x=0;x<temp.length;x++){
            temp[x]=new Ball(x*10,10);
            temp[x].nextBall=x+1;
        }

        temp[0].visible=true;

        return temp;
    }//end initiateBalls

}
