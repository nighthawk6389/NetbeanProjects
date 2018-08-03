/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package browser;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mavis Beacon
 */
public class RandomFacts {
    
    ArrayList<String> facts=new ArrayList<String>();

    public RandomFacts(){
        String filename="randomFacts.txt";
        try {
            BufferedReader r = new BufferedReader(new FileReader(filename));
            String current;
            while((current=r.readLine())!=null){
                if(current.equals(""))
                    continue;
                facts.add(current);
            }
        } catch (Exception ex) {
            Logger.getLogger(RandomFacts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getRandomFact(){
        int index=(int)(Math.random()*facts.size());
        if(index==facts.size())
            index=index-1;
        return facts.get(index);
    }

    public static void main(String args []){

        String filename="randomFacts.txt";
        try {
            BufferedReader r = new BufferedReader(new FileReader(filename));
            String current;
            while((current=r.readLine())!=null){
                if(current.equals(""))
                    continue;
                System.out.println("\""+current+"\"");
            }
        } catch (Exception ex) {
            Logger.getLogger(RandomFacts.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
