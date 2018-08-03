package dataStructures.hw1;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Mavis Beacon
 */
public class MakeChange {
    
    static final int QUATER = 25;
    static final int DIME = 10;
    static final int NICKEL = 5;
    
    public static void main(String args []){
        try{
            if(args.length == 0){
                System.out.println("Usage: java MakeChange <amount>");
                return;
            }
            int amount = Integer.parseInt(args[0]);
            
            if(amount % 5 == 0)
                makeChange(amount,QUATER,"Change for "+ amount +" =");
            else
                System.out.println(amount + " can't be changed");
        }catch(Exception ex){
            System.out.println(ex);
        }
        
    }
    
    public static void makeChange(int amount,int previous, String output){
        
        if(amount <= 0){
            System.out.println(output);
            return;
        }
        
        if( ( QUATER == previous ) && ( amount - QUATER >= 0 ) )
            makeChange( amount-QUATER, QUATER, output + " 25" );

        if( ( QUATER == previous || DIME == previous ) && ( amount - DIME >=0 )  )
            makeChange( amount-DIME, DIME, output + " 10" );

        if( ( NICKEL == previous || DIME == previous || QUATER == previous )
                && ( amount - NICKEL >=0 ) )
            makeChange( amount-NICKEL, NICKEL,  output + " 5"  );
        
    }
}
