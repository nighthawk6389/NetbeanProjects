import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.text.*;

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * Copyright Georgia Institute of Technology 2004-2005
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param width the width of the desired picture
   * @param height the height of the desired picture
   */
  public Picture(int width, int height)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  //Method to remove all blue from a picture
  public void removeBlue()
  {
    Pixel[] pixelArray=this.getPixels();
  Pixel pixelObj;
      int i=0;
      while(i<pixelArray.length)
      {
       pixelObj=pixelArray[i];
        pixelObj.setBlue(0);
        i++;
      }
  }
   public void makeSunset()
  {
    Pixel[] pixelArray=this.getPixels();
  Pixel pixelObj;
      int i=0;
      for( ;i<pixelArray.length;i++)
      {
        pixelObj=pixelArray[i];
       int b=pixelObj.getRed();
       if ((int)(b*1.4)>255)
       {
         pixelObj.setRed(255);
       }
      else
      {
       pixelObj.setRed((int)(b*1.4)); 
      }
      }
  }
   public Picture mirrorVertical()
   {
     Pixel rightPixel=null;
     Pixel leftPixel=null;
     Picture p=new Picture(this);
     for(int y=0;y<this.getHeight();y++)
     {
       for(int x=0;x<this.getWidth()/2;x++)
       {
         leftPixel=this.getPixel(x,y);
         rightPixel=p.getPixel(this.getWidth()-1-x,y);
         rightPixel.setColor(leftPixel.getColor());
       }
     }
     return p;
   }
     public Picture mirrorHorizontal()
   {
     Pixel upPixel=null;
     Pixel downPixel=null;
     Picture p=new Picture(this);
     for(int x=0;x<this.getWidth();x++)
     {
       for(int y=0;y<this.getHeight()/2;y++)
       {
         upPixel=this.getPixel(x,y);
         downPixel=p.getPixel(x,this.getHeight()-1-y);
         downPixel.setColor(upPixel.getColor());
       }
     }
     return p;
   }
     public Picture rotateLeft()
     {
     Pixel originalPixel=null;
     Pixel newPixel=null;
     Picture p=new Picture (this.getHeight(),this.getWidth());
     for(int y=0;y<this.getHeight();y++)
     {
       for(int x=0;x<this.getWidth();x++)
       {
         originalPixel=this.getPixel(x,y);
         newPixel=p.getPixel(y,this.getWidth()-1-x);
         newPixel.setColor(originalPixel.getColor());
       }
     }
     return p;
     }
     public Picture rotateRight()
     {
     Pixel originalPixel=null;
     Pixel newPixel=null;
     Picture q=new Picture (this.getHeight(),this.getWidth());
     for(int y=0;y<this.getHeight();y++)
     {
       for(int x=0;x<this.getWidth();x++)
       {
         originalPixel=this.getPixel(x,y);
         newPixel=q.getPixel(this.getHeight()-1-y,x);
         newPixel.setColor(originalPixel.getColor());
       }
     }
     return q;
     }
     public void copy(Picture p,int startX,int startY,int endX,int endY,int newX,int newY)
     {
       Pixel pixel=null;
       Pixel pixel2=null;
       for (int x=startX,tx=newX; x<endX && x<p.getWidth() && tx<this.getWidth() ;x++,tx++)
       {
         for (int y=startY,ty=newY;y<endY && y<p.getHeight() && ty<this.getHeight();y++,ty++)
         {
           pixel=p.getPixel(x,y);
           pixel2=this.getPixel(tx,ty);
           pixel2.setColor(pixel.getColor());
           
         }
       }
     }

     public void scale(Picture p,double scale)
     {
     Pixel originalPixel=null;
     Pixel newPixel=null;
     for(int y=0;(int)(y/scale)<this.getHeight() && y<p.getHeight();y++)
     {
       for(int x=0;(int)(x/scale)<this.getWidth() && x<p.getWidth();x++)
       {
         originalPixel=this.getPixel((int)(x/scale),(int)(y/scale));
         newPixel=p.getPixel(x,y);
         newPixel.setColor(originalPixel.getColor());
       }
     }
     }
     public void myScale(Picture p,int startX, int startY, int endX, int endY, int newX, int newY, double scaleX, double scaleY)
     {
     Pixel originalPixel=null;
     Pixel newPixel=null;
     int width=(int)(scaleX*(endX-startX));
     int height=(int)(scaleY*(endY-startY));
     for(int ty=newY;ty<(height+newY) && (startY+(ty-newY)/scaleY)<p.getHeight() && ty<this.getHeight();ty++)
     {
       for(int tx=newX;tx<(width+newX) && (startX+(tx-newX)/scaleX)<p.getWidth() && tx<this.getWidth();tx++)
       {
         int x=startX+(int)((tx-newX)/scaleX);
         int y=startY+(int)((ty-newY)/scaleY);
         originalPixel=p.getPixel(x,y);
         newPixel=this.getPixel(tx,ty);
         newPixel.setColor(originalPixel.getColor());
       }
     }
     }
     public void edgeDetect(int difference)
     {
      Color c=null;
      Color c2=null;
      Pixel p=null;
      Pixel p2=null;
      for (int x=0;x<this.getWidth()-1;x++)
        for (int y=0;y<this.getHeight()-1;y++)
      {
        p=this.getPixel(x,y);
        p2=this.getPixel(x,y+1);
        c=p.getColor();
        c2=p2.getColor();
        if(Pixel.colorDistance(c,c2)<difference)
          p.setColor(Color.white);
        else
          p.setColor(Color.black);
      }
     }

} // end of class Picture, put all new methods before this
 