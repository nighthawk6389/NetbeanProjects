import java.awt.*;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.LineBorder;


/*
 * ElevatorControl.java
 *
 * Created on August 29, 2009, 9:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class ElevatorControl extends JPanel implements Runnable{
    Building building;
    Queue<Integer> delayed;
    JList list;
    int [] indices;
    
    public static void main(String arg[]){
        ElevatorControl el=new ElevatorControl();
        JFrame frame= new JFrame();
        frame.setVisible(true);
        frame.setSize(400,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(el);
    }
    /** Creates a new instance of ElevatorControl */
    public ElevatorControl() {    
        building=new Building();
        building.initializeBuilding();
        delayed=new LinkedBlockingQueue<Integer>();

        String [] s=new String[building.getNumOfElevator()*building.getFloors()];
        for(int x=0;x<s.length;x++){
                s[x]=" "+x;
        }
        list=new JList(s);
        list.setBackground(Color.WHITE);
        list.setLayout(new GridLayout(building.getFloors(),building.getNumOfElevator(),5,5));
        list.setLayoutOrientation(list.HORIZONTAL_WRAP);
        list.setVisibleRowCount(10);
        list.setFixedCellWidth(50);
        list.setBorder(new LineBorder(Color.BLACK,1));
        list.setSelectionBackground(Color.black);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        indices=new int[3];
        indices[0]=27;
        indices[1]=28;
        indices[2]=29;
        new Thread(this).start();

        add(list);

        list.setListData(s);
        list.setSelectedIndices(indices);
    }
    
    public void run() {
        do{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ElevatorControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            if((int)(Math.random()*2)==1)
                callElevator((int)(Math.random()*10),"down");
            moveElevators();
             
        }while(stillMoving());
        System.out.println(delayed);
    }
    
    private boolean stillMoving(){
        boolean isMoving=true;
    Elevator [] el=building.getElevators();
        for(int x=0;x<el.length;x++)
            if(el[x].getDirection()=="none"){
                if(delayed.size()>0)
                    callElevator((Integer)delayed.poll(),"down");
            }
            else{
                return true;
            }
    return false;
    }
    private void moveElevators(){
    Elevator [] el=building.getElevators();
        for(int x=0;x<el.length;x++){
            int theFloor=el[x].getFloor();
            if(theFloor==el[x].getFloorCalled()){
                el[x].setDirection("none");
                el[x].setCalled(false);
            }
            if(el[x].getDirection()=="Up")
                el[x].setFloor(theFloor+1);
            
            else if(el[x].getDirection()=="Down")
                el[x].setFloor(theFloor-1);
            indices[x]=29-(el[x].getFloor()*3+x);
            //System.out.println("Index "+x+" is "+(29-(el[x].getFloor()*3+x)));
            list.setSelectedIndices(indices);
            //System.out.println(indices[0]+" "+indices[1]+" "+indices[2]);
            //list.setSelectedIndices(indices);
            //System.out.println("Elevator#"+el[x].getId()+" Moved To"+el[x].getFloor());
            //System.out.println("Elevator Direction: "+el[x].getDirection());
        }
    }
    public boolean callElevator(int floor, String direction){
        //System.out.println("Floor Called:"+floor+" Direction:"+direction);
        System.out.println("Floor "+floor+" is calling");
        Elevator el=getNearestElevator(floor,direction);
        return sendElevator(el,floor);
    }
    private Elevator getNearestElevator(int floor, String direction){
        Elevator [] el=building.getElevators();     
        Elevator temp=null;
        Elevator tempUncalled=null;
        for(int x=0;x<el.length;x++){
           // System.out.println("Elevator#"+el[x].getId()+ " is on floor: "+el[x].getFloor());
            if(el[x].getCalled())
                temp=getNearestElevatorCalled(el[x],temp,floor,direction);
            else if(tempUncalled==null)
                temp=getNearestElevatorUncalled(el[x],temp,floor,direction);
        }  
        if(temp==null)
            addDelayed(floor);
        return temp;
    }//end getNearestElevator
    private Elevator getNearestElevatorCalled(Elevator el,Elevator temp, int floor, String direction){
       // System.out.println("In GetNearesrCalled");
        if(el.getFloor()-floor==0 && el.getDirection()==direction)
               temp=getClosest(temp,el,floor);
         if(el.getFloor()-floor>0 && el.getDirection()=="Down" && direction=="Down")
                temp=getClosest(temp,el,floor);
         if(el.getFloor()-floor<0 && el.getDirection()=="Up" && direction=="Up")
                temp=getClosest(temp,el,floor);
        return temp;
    }
    private Elevator getNearestElevatorUncalled(Elevator el,Elevator temp, int floor, String direction){
       // System.out.println("In GetNearesr-UN-Called");
        if(el.getFloor()-floor==0)
               temp=el;
        temp=getClosest(temp,el,floor);
        return temp;
    }
    private Elevator getClosest(Elevator temp, Elevator elevator, int floor){
        int tempDiff;
        if(temp==null)
            tempDiff=floor;
        else
            tempDiff=Math.abs(temp.getFloor()-floor);
        
        int elevatorDiff=Math.abs(elevator.getFloor()-floor);
        
        if(tempDiff<elevatorDiff)
            return temp;
        else
            return elevator;
    }//end getClosest
    private void addDelayed(int floor){
       // System.out.println("Add to delay: "+floor);
        delayed.add(floor);
    }
    private boolean sendElevator(Elevator el, int floor){
        if(el==null)
            return false;
        el.setDirection(getTheDirection(el,floor));
        el.setFloorCalled(floor);
        el.setCalled(true);
       // System.out.println("In Send "+el);
        return true;
    }
    private String getTheDirection(Elevator el, int floor){
        if(el.getFloor()<floor)
            return "Up";
        else if(el.getFloor()>floor)
            return "Down";
        else
            return "None";
    }

}//end class
