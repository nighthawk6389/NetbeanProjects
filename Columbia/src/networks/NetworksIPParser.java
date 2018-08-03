package networks;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mavis Beacon
 */
public class NetworksIPParser {
    
    
    public static void main3(String args []){
        try {
            List list = new ArrayList();
            FileReader file = new FileReader("C:/american-words.txt.80");
            Scanner scanner = new Scanner(new BufferedReader(file));
            
            while(scanner.hasNext()){
                String word = scanner.next();
                list.add(word);
                System.out.println(word);
            }
            
            Random r = new Random();
            int size = list.size();
            for(int x=0;x<40;x++){
                System.out.println("array["+x+"]=\""+list.get(r.nextInt(size-1))+"\"");
            }
           
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NetworksIPParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main2(String args []){
        try {
            File file = new File("C:\\Users\\Mavis Beacon\\Documents\\School\\Columbia\\ComputerNetworks Spring 2012\\HW#2\\ipOutput.txt");
            Scanner scanner = new Scanner(file);
            
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                if(line.equals(" ") || line.equals("") || line == null)
                    continue;
                if(line.contains("array")){
                    int index = line.indexOf(":");
                    String ip = line.substring(index+3);
                    System.out.print(ip);
                    continue;
                }
                
                if(line.contains("Host")){
                    System.out.print(",Not Found");
                    continue;
                }
                if(line.contains("domain name pointer")){
                    System.out.print("," + line);
                    continue;
                }
                if(line.contains("packets transmitted")){
                    String [] ping = line.split(", ");
                    for(String s: ping){
                        int index = s.indexOf(" ");
                        if(s.contains("time")){
                            System.out.print("," + s.substring(index));
                        }
                        else
                            System.out.print("," + s.substring(0,index));
                    }
                    System.out.println();
                    continue;
                }
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NetworksIPParser.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
    
        public static void main(String args []){
        try {
            File file = new File("C:\\Users\\Mavis Beacon\\Documents\\School\\Columbia\\ComputerNetworks Spring 2012\\HW#2\\randWordHostCheck.txt");
            Scanner scanner = new Scanner(file);
            
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                if(line.equals(" ") || line.equals("") || line == null)
                    continue;
                if(line.contains("Checking")){
                    int index = line.indexOf(" ");
                    String ip = line.substring(index+1,line.length());
                    String com = scanner.nextLine();
                    String edu = scanner.nextLine();
                    String org = scanner.nextLine();
                    System.out.print(ip);
                    System.out.print( com.contains("has address")? ",Found" : ",Not Found");
                    System.out.print( edu.contains("has address")? ",Found" : ",Not Found");
                    System.out.print( org.contains("has address")? ",Found" : ",Not Found");
                    System.out.println();
                    continue;
                }
                /* 
                if(line.contains("has address")){
                    System.out.println(line);
                    //scanner.next();
                    continue;
                }
                if(line.contains("not found")){
                    System.out.println(line);
                    continue;
                }
                 * *
                 */
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NetworksIPParser.class.getName()).log(Level.SEVERE, null, ex);
        } 
       
        }    
}
