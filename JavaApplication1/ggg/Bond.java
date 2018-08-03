package BondList;
import java.io.Serializable;
import java.util.Calendar;

/*
 * Bond.java
 * 
 * Created on September 26, 2008, 12:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class Bond implements Serializable{
    String name; 
    String company;
    DateOf purchased;
    DateOf due;
    DateOf called;
    double yield;
    int worthAtBuy;
    int worthAtSell;
    int worthNow;
    static int order[]=new int[9];
    static boolean made=false;
    
    /** Creates a new instance of Bond */
    public Bond() {
        name="Default";
        company="Default";
        purchased= new DateOf(6,3,89);
        due= new DateOf(6,3,89);
        called=new DateOf(6,3,89);
        yield=0;
        worthAtBuy=1;
        worthAtSell=2;
        worthNow=3;
        if(!made)
            populateOrder();
    }
    public Bond(String name, String company, DateOf purchased, DateOf due, double yield, int worthAtBuy){
        this.name=name; 
        this.company=company;
        this.purchased=purchased;
        this.due= due;
        this.called=new DateOf();
        this.yield=yield;
        this.worthAtBuy=worthAtBuy;
        this.worthAtSell=-1;
        this.worthNow=worthAtBuy;
        //System.out.println("ARRAY WORTH: "+this.worthNow);
        if(!made)
            populateOrder();
    }
    public Bond(Bond bond){
        this.name=bond.getName() ;
        this.company=bond.getCompany();
        this.purchased=bond.getPurchased();
        this.due= bond.getDue();
        this.called=bond.getCalled();
        this.yield=bond.getYield();
        this.worthAtBuy=bond.getWAB();
        this.worthAtSell=bond.getWAS();
        this.worthNow=bond.getWN();
        if(!made)
            populateOrder();
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getCompany(){
        return company;
    }
    public void setCompany(String company){
        this.company=company;
    }
    public DateOf getPurchased(){
        return purchased;
    }
    public void setPurchased(DateOf purchased){
        this.purchased=purchased;
    }
    public DateOf getDue(){
        return due;
    }
    public void setDue(DateOf due){
        this.due=due;
    }
    public DateOf getCalled(){
        return called;
    }
    public void setDateOfCalled(DateOf called){
        this.called=called;
    }
    public double getYield(){
        return yield;
    }
    public void setYield(double yield){
        this.yield=yield;
    }
    public int getWAB(){
        return worthAtBuy;
    }
    public void setWorthAtBuy(int worthAtBuy){
        this.worthAtBuy=worthAtBuy;
    }
    public int getWAS(){
        return worthAtSell;
    }
    public void setWorthAtSell(int worthAtSell){
        this.worthAtSell=worthAtSell;
    }
    public int getWN(){
        changeWorthNow();
        return worthNow;
    }
    public void setWorthNow(int worthNow){
        this.worthNow=worthNow;
    }
    public void populateOrder(){
        if(!made){
            System.out.println("in populate");
            for(int x=0;x<9;x++){
                order[x]=x;
            }
            made=true;
        }
    }
    public static boolean setOrder(int oldNumber,int newNumber){
        System.out.println("Set");
        int temp=order[oldNumber];
        order[oldNumber]=order[newNumber];
        order[newNumber]=temp;
        return true;
    }
    private void changeWorthNow(){
        DateOf today=new DateOf(Calendar.getInstance());
        
        today.add(DateOf.YEAR,1);
        today.setDate(today.month+1,today.day,today.year);
        //System.out.println("After "+today);
        //System.out.println("purchased: "+purchased);
        int greater=today.getYear()-purchased.getYear();
        //System.out.println("greater: "+greater);
        if(greater>0)
            if(today.month<purchased.month){
                    greater--;
            }
            else if(today.month==purchased.month){
                if(today.day<purchased.day)
                    greater--;
            }
        //System.out.println("greaterAfter: "+greater);
        double amount=worthAtBuy;
        for(int x=0;x<greater;x++){
            amount+=yield/100*amount;
        }
        //System.out.println("Amount: "+amount);
        setWorthNow((int)amount);
    }
    public Object[] toArray(){
        
        Object []array= new Object[9];
        System.out.println("To Array "+order[0]); 
        array[order[0]]=this.getName();
        array[order[1]]=this.getCompany();
        array[order[2]]=this.getPurchased();
        array[order[3]]=this.getDue();
        array[order[4]]=this.getCalled();
        array[order[5]]=this.getYield();
        array[order[6]]=this.getWAB();
        array[order[7]]=this.getWAS();
        array[order[8]]=this.getWN();
        
        return array;
    }
}