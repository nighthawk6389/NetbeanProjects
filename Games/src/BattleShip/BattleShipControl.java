package BattleShip;

import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;/*
 * BattleShipControl.java
 *
 * Created on November 24, 2008, 3:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */ 
import javax.swing.border.LineBorder;

/**
 *
 * @author Ilan Elkobi
 */
public class BattleShipControl extends JPanel implements ActionListener, MouseListener{
    Hole holes[];
    int [] iden= new int[6];
    BattleShip playerShips[];
    BattleShipAI aiShips;
    JButton five= new JButton("5");
    JButton four= new JButton("4");
    JButton three= new JButton("3");
    JButton two= new JButton("2");
    JButton six= new JButton("6");
    JButton start=new JButton("Start Game");
    JButton reset=new JButton("Reset");
    JButton clicked;
    JLabel info=new JLabel("Info: ");
    JLabel label=new JLabel(" ");
    JLabel red=new JLabel("Red");
    JLabel yellow=new JLabel("Yellow");
    JLabel blue= new JLabel("Blue");
    JLabel black=new JLabel("Black");
    JLabel green=new JLabel("Green");
    int size=0;
    int AMOUNT_OF_SHIPS=5;
    int GRID_WIDTH=15;
    int GRID_HEIGHT=10;
    boolean align=false;
    boolean setup=true;
    boolean isTurn=true;
    boolean gameOver=false;
    
    public static void main(String args []){
        JFrame frame=new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new BattleShipControl());
        frame.setSize(500,400);
        frame.setLocation(400, 100);
    }
    /** Creates a new instance of BattleShipControl */
    public BattleShipControl() {
        holes=new Hole[301];
        playerShips= createBattleShipSet();
        aiShips= new BattleShipAI(AMOUNT_OF_SHIPS); 

        setLayout(new BorderLayout());
        
        JPanel panel1= new JPanel();
        panel1.setLayout(new GridLayout(GRID_WIDTH,GRID_HEIGHT,0,0));
        //initiate holes
        int var=0;
        for(int x=0;x<holes.length/10;x++){
            for(int y=0;y<10;y++){
                holes[var]=new Hole(var);
                holes[var].addMouseListener(this);
                panel1.add(holes[var]);
                var++;
            }
        }
        holes[holes.length-1]=new Hole(holes.length);
        
        for(int x=0;x<iden.length;x++)
            iden[x]=0;
        
        JPanel panel2= new JPanel();
        panel2.setLayout(new BorderLayout());
        panel2.setBorder(new LineBorder(Color.BLACK, 2));

        JPanel panel3=new JPanel();
        panel3.setLayout(new FlowLayout());
        panel3.add(two);
        panel3.add(three);
        panel3.add(four);
        panel3.add(five);
        panel3.add(six);
        panel3.add(start);
        panel3.add(reset);
        
        six.addActionListener(this);
        two.addActionListener(this);
        three.addActionListener(this);
        four.addActionListener(this);
        five.addActionListener(this);
        start.addActionListener(this);
        start.setEnabled(false);
        reset.addActionListener(this);
        reset.setEnabled(false);

        info.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setForeground(Color.red);
        red.setForeground(Color.red);
        red.setHorizontalAlignment(SwingConstants.RIGHT);
        yellow.setForeground(Color.yellow);
        yellow.setHorizontalAlignment(SwingConstants.RIGHT);
        blue.setForeground(Color.blue);
        blue.setHorizontalAlignment(SwingConstants.RIGHT);
        black.setForeground(Color.black);
        black.setHorizontalAlignment(SwingConstants.RIGHT);
        green.setForeground(Color.green);
        green.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel panel4=new JPanel();
        panel4.setLayout(new GridLayout(7,2));
        panel4.add(info);
        panel4.add(label);
        panel4.add(new JLabel(" "));
        panel4.add(new JLabel(" "));
        panel4.add(red);
        panel4.add(new JLabel("= Your Ships"));
        panel4.add(yellow);
        panel4.add(new JLabel("= Your Miss"));
        panel4.add(green);
        panel4.add(new JLabel("= Your shots that hit"));
        panel4.add(blue);
        panel4.add(new JLabel("= Computer Missed shot"));
        panel4.add(black);
        panel4.add(new JLabel("= Computer shots that hit"));

        panel2.add(panel3,"North");
        panel2.add(panel4,"Center");

        this.addMouseListener(this);
        
        add(panel1,"Center");
        add(panel2,"South");
    }
    
    public BattleShip[] createBattleShipSet(){
        BattleShip temp[]= new BattleShip[AMOUNT_OF_SHIPS];
        for(int x=0;x<temp.length;x++){
            temp[x]= new BattleShip(x+2);
        }
        return temp;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==start){
            setAllComputerShips();
            return;
        }//end
        if(e.getSource()==reset){
            reset();
            return;
        }
        clicked=(JButton)e.getSource();
        size= Integer.parseInt(e.getActionCommand());
        //System.out.println(size+": selected");
        iden=new int[size];
        
    }
    public boolean setPlayerShip(){
        //after right clicking-makes fill in permanent
       // System.out.println("CLICKED");
        try{
            Hole mouseOver=(Hole)findComponentAt(getMousePosition());
            int middle=mouseOver.getId();
            //vertical
            if(!align){
                for(int x=0;x<size;x++){
                  
                    holes[middle+x*20].setFilled(true);
                    holes[middle+x*20].setInUse(true);
                    iden[x]=middle+x*20;
                }
                //horizontal
            } else{
                for(int x=0;x<size;x++){
                    holes[middle-x].setFilled(true);
                    holes[middle-x].setInUse(true);
                    iden[x]=middle-x;
                }
            }
            //check to make sure not overlapping other ships
            
        }catch(Exception ex){
            Integer temp []= {-1};
            //System.out.println("caught "+ex);
            for(int x=0;x<size;x++)
                holes[iden[x]].setInUse(false);
            clearAllNotInUse();//clearSetShip(temp);
            return false;
        }
        return true;
    }//end setPlayeShip

    public boolean setTempComputerShip(int middle, boolean align){
        //after right clicking-makes fill in permanent
        //System.out.println("ComputerTempSet");

        try{
            //vertical
            if(!align){
                for(int x=0;x<size;x++){
                    //holes[middle+x*20].setFilled(true);
                    iden[x]=middle+x*20;
                    if(iden[x]<0 || iden[x]>300)
                        return false;
                }
                //horizontal
            } else{
                for(int x=0;x<size;x++){ 
                    //holes[middle-x].setFilled(true);
                    iden[x]=middle-x;
                    if(iden[x]<0 || iden[x]>300)
                        return false;
                }
            }
        }catch(Exception ex){
            Integer temp []= {-1};
            //System.out.println("caught "+ex);
            clearAllNotInUse();//clearSetShip(temp);
            return false;
        } 
        //clearAllNotInUse();
        return true;
    }//end setComputerShip

    public boolean setComputerShip(int middle, boolean align){
        //after right clicking-makes fill in permanent
        //System.out.println("ComputerSet");

            //vertical
            if(!align){
                for(int x=0;x<size;x++){
                    //holes[middle+x*20].setFilled(true);
                    holes[middle+x*20].setInUse(true);
                    iden[x]=middle+x*20;
                } 
                //horizontal
            } else{
                for(int x=0;x<size;x++){
                    //holes[middle-x].setFilled(true);
                    holes[middle-x].setInUse(true);
                    iden[x]=middle-x;
                }
            }
  
        return true;
    }//end setComputerShip

    private void clickedSetup(MouseEvent e){
         //call to make permanent
       // System.out.append("ClickedSetup");
        if(e.getButton()==MouseEvent.BUTTON1){
            if(size!=0){
                nextStep();
            }//end if
        } else if(e.getButton()==MouseEvent.BUTTON3){
            //horizontal-vertical 
            align=!align;
           // System.out.println("ALIGN: "+align);
        }
        //align false=vertical  true=horizontal

        if( !two.isEnabled() && !three.isEnabled() && !four.isEnabled() && !five.isEnabled() && !six.isEnabled()){
            start.setEnabled(true);
            setup=false;
        }
    }//end clickedSetup

    private void nextStep(){

        if(isTurn){
            if(!checkShip()){
                //System.out.println("Bad Check");
                clearAllNotInUse();
                return;
            }
            if(setPlayerShip()){
               // System.out.println("Set Check True");
                setButton();
                storeToShips(true);
            }//end if
        }//end if

        for(int x=0;x<iden.length;x++){
            iden[x]=holes.length-1;
        }//end for

        size=0;

    }//end nextStep 

    private void setAllComputerShips(){

        int sizeOfShips=0;
        int [] info;
        this.size=2;
        boolean compAlign;
        while(sizeOfShips<5){
            iden=new int[size];
            //System.out.println("In While");
            info=aiShips.getBattleShip();
            compAlign=(info[1]==0)?(true):(false);
            //System.out.println("Place: "+info[0]+" CompAlign: "+compAlign);
            if(setTempComputerShip(info[0],compAlign)){
                //System.out.println("Set temp");
                if(checkShip() && checkComputerShip()){
                   // System.out.println("Good Check");
                    setComputerShip(info[0],compAlign);
                   // System.out.println("Set permanent");
                    storeToAIShips();
                    sizeOfShips++;
                    size++;
                }//end if
            }
        }//end while
        clearAllNotInUse();
        start.setEnabled(false);
        
        for(int x=0;x<iden.length;x++){
            iden[x]=holes.length-1;
        }//end for

        size=0;
    }//end setComputerShips

    public void clickedDestroy(MouseEvent e){
       // System.out.println("Destroy");
        
        Hole mouseOver=(Hole)findComponentAt(getMousePosition());
        int middle=mouseOver.getId();
        
        BattleShip [] computerShips=aiShips.ai;
        Hole [] aiHoles;
        for(int x=0;x<computerShips.length;x++){
            aiHoles=computerShips[x].getPlace();
            for(int y=0;y<aiHoles.length;y++){
                int id=aiHoles[y].getId();
                if(middle==id){
                    goodHit(computerShips[x],aiHoles[y],false);
                    
                    getComputerDestroy();
                    return;
                }
            }//end for
        }//end for

        missHit(mouseOver,false);
        getComputerDestroy();
        return;
    }//end clickedDetroy

    public void goodHit(BattleShip ship,Hole hole, boolean isFriendly){
        hole.setFriendlyHit(isFriendly);
        hole.setHit(true);
        
        Hole[] compHoles=ship.getPlace();
        for(int x=0;x<compHoles.length;x++){
            if(compHoles[x].getId()==hole.getId()){
                compHoles[x].setHit(true);
            }
        }

        int isOver=gameOver();
        if(isOver==1){
            reset.setEnabled(true);
            label.setText("User Won!!");
        }//end if
        else if(isOver==2){
            reset.setEnabled(true);
            label.setText("Computer Won!");
        }
    }//end goodHit

    private void missHit(Hole hole,boolean isAI){
        //System.out.println("missHit");
        if(!isAI)
            hole.setMiss(true);
        else{ 
            hole.setComputerMiss(true);
        }//end else
    }//end missHit

    private void getComputerDestroy(){

        holes[aiShips.lastShotAddress].setComputerMiss(false);
        
        int address=aiShips.getDestroyShip();
        Hole [] playerHoles;
        for(int x=0;x<playerShips.length;x++){
            playerHoles=playerShips[x].getPlace();
            for(int y=0;y<playerHoles.length;y++){
                int id=playerHoles[y].getId();
                if(address==id){
                    if(!aiShips.getLastShotHit() && (aiShips.missAfterHit==-1 || aiShips.missAfterHit>3)){
                        //System.out.println("First Address Set: "+id);
                        aiShips.setFirstHitAddress(id);
                    }
                    aiShips.setLastShotHit(true);
                    //System.out.println("Computer Hit");
                    goodHit(playerShips[x],playerHoles[y],true);
                    return; 
                }
            }//end for 
        }//end for

        aiShips.setLastShotHit(false);
        //System.out.println("Computer Miss");
        missHit(holes[address],true);
    }

    public int gameOver(){
        BattleShip [] computerShips=aiShips.ai;
        Hole [] place;
        int error=0;
        for(int x=0;x<computerShips.length;x++){
            place=computerShips[x].getPlace();
            for(int y=0;y<place.length;y++){
                if(!holes[place[y].getId()].getHit())
                    error++;
            }//end for
        }//end for

        if(error==0){
            gameOver=true;
            return 1;
        }
        error=0;
        for(int x=0;x<playerShips.length;x++){
            place=playerShips[x].getPlace();
            for(int y=0;y<place.length;y++){
                if(!holes[place[y].getId()].getHit())
                    error++;
            }//end for
        }//end for

        if(error==0){
            gameOver=true;
            return 2;
        }

        System.out.println("Error: "+error);
        return 0;
    }//end gameVOer

    private void reset(){

        playerShips= createBattleShipSet();
        aiShips= new BattleShipAI(AMOUNT_OF_SHIPS);
        //initiate holes
        int var=0;
        for(int x=0;x<holes.length;x++){
                holes[x].resetHole();
        }
        for(int x=0;x<iden.length;x++)
            iden[x]=0;

        two.setEnabled(true);
        three.setEnabled(true);
        four.setEnabled(true);
        five.setEnabled(true);
        six.setEnabled(true);
        reset.setEnabled(false);
        start.setEnabled(false);
        label.setText(" ");

        setup=true;
        gameOver=false;
    }//end reset

    @Override
    public void mouseClicked(MouseEvent e) {
       // System.out.println("Mouse Clicked");

        if(gameOver)
            return;
        
        if(!isTurn)
            return;

        if(setup){
            clickedSetup(e);
        }
        else{
            clickedDestroy(e);
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        if(size!=0){
            //display where ship would go if clicked
            try{
                Hole mouseOver=(Hole)findComponentAt(getMousePosition());
                int middle=mouseOver.getId();
                if(!align){
                    for(int x=0;x<size;x++){
                        holes[middle+x*20].setFilled(true);
                        iden[x]=middle+x*20;
                    }
                } else{
                    for(int x=0;x<size;x++){
                        holes[middle-x].setFilled(true);
                        iden[x]=middle-x;
                    }
                }
                
                
            }catch(Exception ex){
                //System.out.println("caught: " +ex);
            }//end catch
            
        }//end if
    }//end mouseEntered
    
    @Override
    public void mouseExited(MouseEvent e) {
        //erase previous mouseEntered
        if(size!=0){
            for(int x=0;x<iden.length;x++){
                holes[iden[x]].setFilled(false);
            }
            
        }
    }
    
    private void setButton() {
       // System.out.println("In Set Button");
        clicked.setEnabled(false);
        System.out.println(clicked.getText());
    }
    
    private void storeToShips(boolean player) {
        Hole [] temp= new Hole[size];
        for(int x=0;x<size;x++){
            temp[x]=holes[iden[x]];
        }
        if(player){
            playerShips[size-2].setPlace(temp);
            playerShips[size-2].setUsed(true);
          //  System.out.println("Player"+(size-2)+" is: "+playerShips[size-2].getUsed());
        }
        
    }
        private void storeToAIShips() {
        Hole [] temp= new Hole[size];
        for(int x=0;x<size;x++){
            temp[x]=holes[iden[x]];
            temp[x].setAI(true);
           // System.out.println("AiShip at: "+iden[x]);
        }
            aiShips.ai[size-2].setPlace(temp);
            aiShips.ai[size-2].setUsed(true);

    }
    private boolean checkShip() {
        boolean q=true;
        ArrayList<Integer> list= new ArrayList<Integer>();
        //System.out.println("PlayerShips Length: "+ playerShips.length);
        for(int x=0;x<playerShips.length;x++){
           // System.out.println("PlayerShips: " +x +"Used? "+playerShips[x].getUsed());
            if(!playerShips[x].getUsed())
                continue;
            for(int y=0;y<playerShips[x].getPlace().length;y++){
              //  System.out.println("PlayerShip "+x+" PlayerShips.GetPlaceLength " +playerShips[x].getPlace().length);
                for(int z=0;z<iden.length;z++){
                //    System.out.println("Iden.length " +iden.length);
                    q=(playerShips[x].getPlace()[y].getId()==iden[z]);
                    System.out.println(q);
                 //   System.out.println("X: "+x+" Y: "+y+" Z: "+z+"getId: "+playerShips[x].getPlace()[y].getId()+" iden[z]: "+iden[z]);
                    if(q){
                        list.add(iden[z]);
                    }//end if
                }//end for 3
            }//end for 2
        }//end for 1
        Integer [] temp=new Integer[1];
        temp=(Integer[]) list.toArray(temp);
        //clearSetShip(temp);
       // System.out.println("Q: "+q);
       // System.out.println(list);
        return (list.isEmpty());
    }//end checkShip

    private boolean checkComputerShip() {
        boolean q=true;
        ArrayList<Integer> list= new ArrayList<Integer>();
        BattleShip [] aiShipsArray=aiShips.ai;
        //System.out.println("aiShipsArray Length: "+ aiShipsArray.length);
        for(int x=0;x<aiShipsArray.length;x++){
            //System.out.println("aiShipsArray: " +x +"Used? "+aiShipsArray[x].getUsed());
            if(!aiShipsArray[x].getUsed())
                continue;
            for(int y=0;y<aiShipsArray[x].getPlace().length;y++){
                //System.out.println("PlayerShip "+x+" aiShipsArray.GetPlaceLength " +aiShipsArray[x].getPlace().length);
                for(int z=0;z<iden.length;z++){
                   // System.out.println("Iden.length " +iden.length);
                    q=(aiShipsArray[x].getPlace()[y].getId()==iden[z]);
                   // System.out.println(q);
                   // System.out.println("X: "+x+" Y: "+y+" Z: "+z+"getId: "+aiShipsArray[x].getPlace()[y].getId()+" iden[z]: "+iden[z]);
                    if(q){
                        list.add(iden[z]);
                    }//end if
                }//end for 3
            }//end for 2
        }//end for 1
        Integer [] temp=new Integer[1];
        temp=(Integer[]) list.toArray(temp);
        //clearSetShip(temp);
        System.out.println("Q: "+q);
        System.out.println(list);
        return (list.isEmpty());
    }//end checkShip
    
    private void clearSetShip(Integer [] temp) {

        for(int x=0;x<size;x++){
            //if(temp[x]==null)
                //break;
            for(int y=0;y<temp.length;y++){
                try{
                if(((int)(iden[x]))== temp[y]){
                    holes[iden[x]].setInUse(true);
                    holes[iden[x]].setFilled(true);
                } else{
                    holes[iden[x]].setInUse(false); 
                    holes[iden[x]].setFilled(false);
                }//end else
                }catch(Exception e){System.out.println("hello");}
            }//end for
        }//end for
    }//end clearSetShip
 
    private void clearAllNotInUse(){
        System.out.println("Clearing");
        for(int x=0;x<holes.length;x++){
            if(holes[x].getFilled() && !holes[x].getInUse()){
                holes[x].setFilled(false);
            } 
        }
    } 
}

//TO DO: 
//       Create random button
//       
//       Winning
//       Information tab on bottom 
//       