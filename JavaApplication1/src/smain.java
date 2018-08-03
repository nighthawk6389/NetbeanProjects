//package snake;

import java.awt.*;
import java.awt.event.*; 
import java.applet.*;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Event;

public class smain extends Applet implements Runnable{

  // 15 - rows, 25 - columns
  boolean game = false, ate=false, game2=false, playing=false;
  int size, hsize=0;

  Point[] places = new Point[374];

  int dir = 0;  // 0 - right
                // 1 - left
                // 2 - up
                // 3 - down

  Point apple = new Point(12,7);

  Thread runner;

  public void init() {
          size=3;

          for(int i=0; i<374; i++)
                  places[i] = new Point(0,0);

          places[0].move(3,15);
          places[1].move(2,15);
          places[2].move(1,15);
  }

  public void start() {
          if(runner==null) {
                  runner = new Thread(this);
                  runner.start();
          }
  }

  public void stop() {
          if(runner!=null) {
                  runner.stop();
                  runner = null;
          }
  }

  public void run() {

          while(true) {

                  // All possible game overs - wall crashings or crashing into your own snake

                  for(int i=5; i<size; i++)
                          if(places[0].x == places[i].x && places[0].y == places[i].y)
                                  game2 = true;

                  if(( (game2) || (dir==0 && places[0].x==25) || (dir==1 && places[0].x==1) || (dir==2 && places[0].y==1) || (dir==3 && places[0].y==15))) {
                          // Setting all starting points again
                          game = false;
                          game2 = false;
                          if(size>hsize) hsize=size;
                          size=3;
                          places[2].move(1,15);
                          places[1].move(2,15);
                          places[0].move(3,15);
                          apple.move(12,7);
                  }

          repaint();

          try{ Thread.sleep(70); }
          catch(InterruptedException e) { }

          }
  }

  public void update(Graphics g) {
          g.drawRect(apple.x*20-20,apple.y*20-20,19,19);
          if(game) {

                  if(places[0].x==apple.x && places[0].y==apple.y) {
                                  ate=true;
                                  size++;
                                  g.clearRect(0,0,size().width,12);
                          }
                  // Drawing new head position, erasing last tale position

                  g.clearRect(places[size-1].x*20-20,places[size-1].y*20-20,20,20);

                  if(places[size-1].x == apple.x && places[size-1].y == apple.y)
                          g.drawRect(apple.x*20-20,apple.y*20-20,19,19);

                  if(playing) {
                          for(int i=size; i>0; i--)
                                  places[i].move(places[i-1].x, places[i-1].y);

                  switch(dir) {
                                  case 0: places[0].x = places[0].x+1; break;
                                  case 1: places[0].x = places[0].x-1; break;
                                  case 2: places[0].y = places[0].y-1; break;
                                  case 3: places[0].y = places[0].y+1; break;
                          }}

                  // Checking if had just eaten an apple, if yes - draw new apple location
                  if(ate) {
                          ate = false;
                          g.clearRect(apple.x*20-20,apple.y*20-20,21,21);
                          apple.x=(int)(Math.random()*24+1);
                          apple.y=(int)(Math.random()*14+1);
                          g.drawRect(apple.x*20-20,apple.y*20-20,19,19);
                  }

                  for(int i=0; i<size; i++)
                          g.fillOval(places[i].x*20-20,places[i].y*20-20,20,20);
          } else {
                  // Clears screen, and draws again apple and new body
                  g.clearRect(0,0,size().width,size().height);

                  for(int i=0; i<size; i++)
                          g.fillOval(places[i].x*20-20,places[i].y*20-20,20,20);

                  g.drawRect(apple.x*20-20,apple.y*20-20,19,19);
          }
                  g.drawString("Score: "+size+"           Heighest score till now: "+hsize,10,10);
  }

  public boolean keyDown(Event evt, int key) {  
        if(key == Event.RIGHT && places[1].x!=places[0].x+1) { dir=0; game=true; playing=true; return true; }
        else if(key == Event.LEFT && places[1].x!=places[0].x-1) { dir=1; game=true; playing=true; return true; }
        else if(key == Event.UP && places[1].y!=places[0].y-1) { dir=2; game=true; playing=true; return true; }
        else if(key == Event.DOWN && places[1].y!=places[0].y+1) { dir=3; game=true; playing=true; return true; }
        else if(key == 112 || key == 1508) {
                playing=false;
                return true;
        }
        return false;
  }
}