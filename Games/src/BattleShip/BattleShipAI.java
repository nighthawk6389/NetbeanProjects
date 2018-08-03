package BattleShip;


import java.util.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mavis Beacon
 */
public class BattleShipAI implements Runnable{

    BattleShip ai [];
    boolean [] enemyShipsHit;
    int direction=1;
    List<Integer> misses=new ArrayList<Integer>();
    List<Integer> hits=new ArrayList<Integer>();
    int missAfterHit=-1;
    int firstHitAddress;
    int lastShotAddress;
    boolean lastShotHit;

    public BattleShipAI(int size){
        this.ai=createBattleShipSet(size);
        this.enemyShipsHit=new boolean[size];
    }//end default
    public BattleShipAI(BattleShip [] ai){
        this.ai=ai;
    }//end constructor
    public void setAI(BattleShip [] ai){
        this.ai=ai;
    }//end setAi
    public BattleShip [] getBattleShips(){
        return ai;
    }//end getAi

    public static BattleShip[] createBattleShipSet(int AMOUNT_OF_SHIPS){
        BattleShip temp[]= new BattleShip[AMOUNT_OF_SHIPS];
        for(int x=0;x<temp.length;x++){
            temp[x]= new BattleShip(x+2);
        }
        return temp;
    }//end createBattleShipSet

    public int [] getBattleShip(){
        int [] info=new int[2];
        info[0]=(int)(Math.random()*300);
        info[1]=(int)(Math.random()*1.9);
        return info;
    }//end getBattleShip

    public int getDestroyShip(){
       // System.out.println("In getDestoryShip");
        int address;
        if(lastShotHit){
       //     System.out.println("LastShotHit");
            address=getAddedAddress(true);
            missAfterHit=0;
        }
        else if(missAfterHit!=-1 && missAfterHit<3){
        //    System.out.println("Miss AfterHit");
            address=getAddedAddress(false);
        }
        else{
            address=getRandomMove();
            missAfterHit=-1;
        }//end else

       lastShotAddress=address;
      // System.out.println("Returning Address: "+address);
       return address;
    }//end getShip

    private int getAddedAddress(boolean wasLastHit){

        int address;
        boolean goAgain=false;
        int loopCounter=0;
        do{
            address=lastShotAddress;
            if(!wasLastHit){
                direction++;
                address=firstHitAddress;
            }//end if


            if(direction>4)
                    direction=1;
            
            if(direction==1)
                address=address+1;
            else if(direction==2)
                address=address+1*20;
            else if(direction==3)
                address=address-1;
            else if(direction==4)
                address=address-1*20;

            goAgain=(address<0 || address>300 || misses.contains(address) || hits.contains(address))&& loopCounter<4;
            if(goAgain){
                direction++;
                loopCounter++;
            }
        }while(goAgain);

       // System.out.println("Loop Counter: "+loopCounter);
        if(loopCounter>=4){
          //  System.out.println("Loop Counter went 4!!!");
            address=getRandomMove();
        }


       return address;
    }//end getAdded

    private int getRandomMove(){
       // System.out.println("In Random");
        missAfterHit=-1;
        int idX=(int)(Math.random()*300);
        while(misses.contains(idX) || hits.contains(idX))
             idX=(int)(Math.random()*300);
        
        return idX;
    }//end getRandom

    public void addToMiss(){
        misses.add(lastShotAddress);
    }
    public void addToHit(){
        hits.add(lastShotAddress);
    }
    public void setLastShotHit(boolean isHit){
        if(isHit)
            addToHit();
        else
            addToMiss();
        lastShotHit=isHit;
    }
    public void setFirstHitAddress(int fha){
        this.firstHitAddress=fha;
    }
    public boolean getLastShotHit(){
        return lastShotHit;
    }
   


    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
