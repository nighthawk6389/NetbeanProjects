package dataStructures.hw2;

/* InchwormNode
 */


/** A Inchworm is a sequence of segments, represented as a Linked list
    of InchwormNode.  Each InchwormNode contains a Segment and a
    pointer to the next node.  A inchworm can be created: grow: (adds
    another segment at the end) shrink: (loses the segment just behind
    the head), but it never getrs smaller than 1 node.  move: (move
    forward, with each segment following the one ahead of it) */


import java.util.*;
import java.awt.Color;

public class InchwormNode{

  private Segment segment;    // the info about this segment of the inchworm
  private InchwormNode next;    // the next segment of the inchworm

  // There are two constructors

  /** Constructor for the head of a new inchworm.
      A new inchworm should 
        be just one segment long,
        be in a random position,
        have a random direction,
        have a random color (which changes along the segments -
	each segment is a different hue from the previous one)
  */
  public InchwormNode(){
    segment = new Segment();         // Make a head segment
    next = null;
  }

  /** Constructor for a segment to be attached to a previous segment:
      - position is one radius over from the x and y of the previous segment
        (in the opposite direction from the prevDir),
      - moving in the same direction as the previous segment
      - color is the next shade through the spectrum from the previous segment
   */
  public InchwormNode(Segment prevSegment){ 
    segment = new Segment(prevSegment);
    next = null;
  }

  /* true if the head of this inchworm is near to a position */
  public boolean near(int x, int y){
    return segment.near(x,y);
  }
  

  /** Return the position of the head of the inchworm */

  public int getX(){
    return segment.getX();
  }

  public int getY(){
    return segment.getY();
  }

  /** move: the inchworm should move along, with each segment
       following the one in front of it. That means that each segment
       must move in the direction the previous segment moved the last
       time.  The version of move with no arguments will work out a
       new direction to move (most commonly the same as the current
       direction of the head) then call move(newDirection).  It will
       turn if it is over the edge, or with a 1 in 10 probability.
       The version of move with a new direction should first tell the
       segment after it to move in this segment's current direction,
       then it should move itself in the new direction, and remember
       this new direction.  Note, move doesn't cause it to redraw - it
       just changes the current position.  */

  public void move(){
    String newDir = segment.getNewDirection();
    move(newDir);
  }    

  public void move(String newDir){
    String currentDir = segment.getDirection();

    if (next!=null)
      next.move(currentDir);

    segment.setDirection(newDir);
    segment.move();
  }    
 

  /** redraw should first redraw the rest of the inchworm, then it
       should draw this segment.  The segment should draw itself as a
       colored circle, centered at its position.  If it is a face, It
       should draw two eyes just above its center */

  public void redraw(DrawingCanvas canvas){
 // (temporary code that draws just the head, makes the template compile)
    
    if(next != null) {
      next.redraw(canvas);
    }
    segment.redraw(canvas); 
  }


  /* Grow will add a new segment onto the end of the inchworm
     It should have the appropriate position and direction.
  */
  public void grow(){
      //YOUR CODE HERE
      if( next == null){
          next = new InchwormNode(segment);
          return;
      }
      next.grow();
        
  }


  /* As long as there are at least two segments, shorten should remove
     the very last segment from the inchworm (ie, the last node of the list  */

  public void shorten(){
    // YOUR CODE HERE
      if( next == null)
          return;
      if(next.next == null){
          next = null;
          return;
      }
      next.shorten();
      
  }


  /** Reverse will reverse the inchworm, turning the head into the
      tail and the tail into the head, reversing all the links along
      the way, and returning a reference to the new head Note, it is
      also necessary to replace the direction of travel in each
      segment by its opposite .*/

  public InchwormNode reverse() {
      // YOUR EXTRA CREDIT CODE HERE
      
      InchwormNode inchworm = this;
      this.segment.reverseDirection();
      this.segment.setFace(false);
      if( next == null ){
          this.segment.setFace(true);
          return inchworm;
      }
      inchworm = next.reverse();
      next.next = this;
      next=null;
      return inchworm;

  }
   
  public InchwormNode split(){
      if(next == null)
          return null;
      InchwormNode newInchy = next;
      newInchy.segment.setFace(true);
      next = null;
      return newInchy;
  }
  
}

