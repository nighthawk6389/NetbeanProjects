/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SlimeBall;

import java.awt.Point;

/**
 *
 * @author Mavis Beacon
 */
public class BasketBall extends AnimatedObject {
    protected final String shape="Circle";
    protected final int SIZEBALL=this.size=20;

    public BasketBall(){
        super();
    }
    public BasketBall(int xPos, int yPos){
        super(xPos,yPos);
    }

    public String getShape(){
        return shape;
    }


    
    public void setCollisionVelocity(Point p,int offSet){
        if(p.x==this.xPos && p.y==this.yPos){
            setYCollision(p);
            setXCollision(p,offSet);
        }
    }//end setCollisionVelocity

    public void setXCollision(Point p, int offSet){
        //P IS NOT IMPLEMENTED YET< JUST FOR UNIFORMITY
        if(this.xPos<p.x)
            setLeftSideWallCollision(p,offSet);
        else if(this.xPos>p.x)
            setRightSideWallCollision(p,offSet);

        this.xVelocity=-this.xVelocity;
        if(this.xVelocity==0)
            this.xVelocity=5;
        this.currentYVelocity+=5;
    }//end setCollisionXVelocity

    public void setYCollision(Point p){
       super.yPos=p.y;
       this.setYVelocity(-this.getYVelocity());
       this.setDecreaseXVelocity(1);
       this.setTime(.5);

    }//end setCollisionYVelocity

    private void setLeftSideWallCollision(Point p,int offSet){
        this.xPos=p.x+offSet;
    }//end leftSidewallCollision

    private void setRightSideWallCollision(Point p,int offSet){
        this.xPos=this.xPos-p.x-offSet;
    }//end leftSidewallCollision



    public void setCollisionWithPlayerVelocity(AnimatedObject p){
        String side="";
       System.out.println("In Collision with player");
        if(p.xPos>this.xPos){
           System.out.println("1");
            this.xPos=p.xPos-this.size;
            side="right";
        }
        else if(p.xPos<this.xPos){
            System.out.println("2");
            this.xPos=p.xPos+p.size+1;
            side="left";
        }


        setXPlayerCollision(p,side);
        setYPlayerCollision(p);
    }//end setCollisionVelocity

   public void setYPlayerCollision(AnimatedObject p){
       System.out.println("Bothe Velocities: "+this.currentYVelocity+" "+p.currentYVelocity);
       this.currentYVelocity=-(this.currentYVelocity-p.currentYVelocity)*2;
       System.out.println("Ball Velocity(combined): "+this.currentYVelocity);
       this.setTime(2);
    }//end setCollisionYVelocity

    public void setXPlayerCollision(AnimatedObject p,String side){
        //P NOT IMPLEMENTED YET
        int sign=1;
        if(side.equals("right"))
            sign=-1;
        this.xVelocity=(-this.xVelocity);//p.xVelocity;
        if(this.xVelocity==0 && this.yVelocity==0)
            this.xVelocity=sign*10;
        System.out.println("After settingXPlayerCollision, ball Xvelocity is: "+this.xVelocity);
    }//end setCollisionYVelocity
}//end basketBall
