package dataStructures.hw2;

/* Inchworm Simulation
 */

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.*; 


/** Simulation of an inchworm, squirming around the screen
   looking for food sources.  If it finds a food source, it eats it. 
   Different kinds of food will have different effects.

   The center of the program is the run() method that loops forever,
   controlling the simulation.
   On each cycle ("clock tick") it
   - moves the inchworm,
   - makes it eat,
   - redraws it, and
   - waits for the next clock tick.

*/

public class InchwormSimulation implements ActionListener{

// Fields
  private JFrame frame;
  private DrawingCanvas canvas; 
  private JTextArea messageArea;   

  private Vector<InchwormNode> inchworm;   
    
  private LinkedList<FoodSource> foodSources;   // the size and color of each atom type.
  private boolean keepRunning = true;

// Constructors
  /** Construct a new InchwormSimulation object
   * and set up the GUI
   */
    public InchwormSimulation(){
    frame = new JFrame("Inchworm Frenzy");
    frame.setSize(500, 500);

    //The graphics area
    canvas = new DrawingCanvas(); 
    frame.getContentPane().add(canvas, BorderLayout.CENTER);

    //The message area, mainly for debugging.
    messageArea = new JTextArea(1, 80);     //one line text area for messages.
    frame.getContentPane().add(messageArea, BorderLayout.SOUTH);

    JPanel buttonPanel = new JPanel(new java.awt.GridLayout(2,0));
    frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);

    addButton(buttonPanel, "Reverse").setForeground(Color.black);
    addButton(buttonPanel, "Quit").setForeground(Color.red);;
    addButton(buttonPanel, "Shorten").setForeground(Color.blue);;
    addButton(buttonPanel, "Grow").setForeground(Color.orange);;
    addButton(buttonPanel, "Split").setForeground(Color.green);;

    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

//  // GUI Methods

  /** Helper method for adding buttons */
  private JButton addButton(JPanel panel, String name){
    JButton button = new JButton(name);
    button.addActionListener(this);
    panel.add(button);
    return button;
  }

  /** Respond to button presses */

  public void actionPerformed(ActionEvent e){
    String cmd = e.getActionCommand();
    if (cmd.equals("Quit") ) {
      keepRunning= false;  // to make the main thread stop
      frame.dispose();
    } else if (cmd.equals("Reverse")){
        for(int i = 0 ; i < inchworm.size() ; i++){
            InchwormNode newHead = (inchworm.get(i)).reverse();
            inchworm.set(i, newHead); 
        } 
    } else if(cmd.equals("Grow")){
         for(InchwormNode inchy : inchworm)
            inchy.grow();  //add this method in InchwormNode.java
    } else if(cmd.equals("Shorten")){
        for(InchwormNode inchy : inchworm)
            inchy.shorten(); //add this method in InchwormNode.java
    } else if(cmd.equals("Split")){
        int size = inchworm.size();
        for(int i = 0; i < size ; i++){ 
            InchwormNode newInchy = inchworm.get(i).split();
            if(newInchy != null)
                inchworm.add(newInchy);
        }
    } else 
	throw new RuntimeException("No such button: "+cmd);
  }


  public void reset(int numFoodSources){
    foodSources = new LinkedList<FoodSource>();
    for (int i=0; i<numFoodSources; i++)
       foodSources.add(new FoodSource());
    inchworm = new Vector<InchwormNode>();
    inchworm.add( new InchwormNode());
    redraw();
  }


  /** A never ending simulation loop that waits for the next tick of
      the simulation, then - move the inchworm, possibly changing
      its direction - checks the inchworm to see if it is next to
      some food if so, makes it eat the food
  */
  public void run(){
    while (keepRunning) {
      // move the inchworm
        
      for(InchwormNode inchy: inchworm)  
        inchy.move();

      eat();   // check each inchworm against each food supply

      redraw(); //redraw the canvas after eating
//slows down the computation with arbitrary delay
      try { Thread.sleep(500); } catch (Exception e) { }
    }  
  }
 

  /** Check if the inchworm is at one of the food sources. If so,
      perform the appropriate action on the inchworm, depending on the
      type of the food source.  

      Once food is eaten, it needs to be deleted from the foodSources Linked List
      which is then redrawn to reflect the eaten space

*/

  public void eat(){
      ArrayList<FoodSource> delete = new ArrayList<FoodSource>();
      for(InchwormNode inchy : inchworm){
          for (FoodSource food : foodSources){
            if ( inchy.near(food.getX(), food.getY())){
              switch(food.getType()){
              case Nutritious :
                inchy.grow(); break;
              case Wasting :
                inchy.shorten(); break;
              }
              delete.add(food);
            }
          }
      }
      foodSources.removeAll(delete);
    } 


  public void redraw(){
    canvas.clear(false);
    for (FoodSource fs : foodSources) fs.redraw(canvas);
    
    for(InchwormNode inchy:inchworm)
        inchy.redraw(canvas);
    canvas.display();
  }

  public static void main(String args[]){
      InchwormSimulation worm = new InchwormSimulation();
      if(args.length==0) {
          worm.reset(80);
      } else { 
         int morsels=Integer.parseInt(args[0]);
         worm.reset(morsels);  // 80 food sources
      }
      worm.run();
  }
}




  


