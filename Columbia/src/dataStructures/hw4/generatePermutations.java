package dataStructures.hw4;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
public class generatePermutations {
    
    public static void main(String args []){
        
                int TABLE = 46;
        int START = 2;
        int WORD_SIZE = 6; 
        
        LinkedList<String> permutations = new LinkedList<String>();
        LinkedList<String> newPermutations = new LinkedList<String>();
        int debugBreakCounter = 0;
        int array [];
        LinkedList<String> goodPermutations;
        LinkedList<String> zeroPermutations;
        ArrayList<String> wordsLessThan;
        
        for(int i = 0; i < WORD_SIZE; i++){
                permutations.add("-"+(i+1)+"-");
            }
        
         for(int i = 0; i < WORD_SIZE -1  ; i++){
                //System.out.println(" i = " + i);
                while( !permutations.isEmpty() ){
                    String next = permutations.pop();
                    String [] nextArray = next.split("-");
                    //System.out.println("Next Array: " + Arrays.toString(nextArray)); 
                    for(int k = 0; k < WORD_SIZE ; k++){
                            if(next.contains("-" + (k+1) + "-" ) || next.contains( "-" + (k+1) + "-" ) ){
                                //System.out.println("Next : " + next + ". Skipping with " + (k+1));
                                continue;
                            }
                            //System.out.println("Adding to " + next + " " + (k+1));
                            
                            newPermutations.add( next + (k+1) + "-");
                           
                       // System.out.println("new Permutations are now: " + newPermutations);
                    }
                } 
            permutations = newPermutations;
            newPermutations = new LinkedList<String>();
         }
            
       // }
        System.out.println("Found " + permutations.size() + " permutations for WORD_SIZE = "  + WORD_SIZE);
        //System.out.println(permutations + "\n" + permutations.size());
        
                Scanner scanner = null;
        try {
            scanner = new Scanner(new File("C:\\Users\\Mavis Beacon\\Documents\\School\\Columbia\\DataStructures 2012\\HW#4\\reserved_words.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HashFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
                String [] listOfWords = new String [46];
        int counter = 0;
        while(scanner.hasNext()){
            listOfWords[counter++] = scanner.next();
        }
        
            goodPermutations = new LinkedList<String>();
            zeroPermutations = new LinkedList<String>();
            wordsLessThan = new ArrayList<String>();
            ArrayList<Integer> list = new ArrayList<Integer>();
            
            for(String next : listOfWords){
                if( next.length() <= WORD_SIZE ){
                    wordsLessThan.add(next);
                }
            }
        
            int permCounter = 0;
            for(String permute : permutations){
                permCounter++;
                String [] permuteWordArray = permute.split("-");
                
                int bestNum = 1;
            int bestCollisions = 7;
                for(int z = 0; z < 100; z++){
                int collisions = 0;
                array = new int[46];
                for(String next : wordsLessThan){
                    char [] charArray = next.toCharArray();
                    int hashVal = 0; 
                    int length = permuteWordArray.length;
                    //System.out.println(Arrays.toString(permuteWordArray));
                    for(int x = 0; x < length - 1 ; x++){ // the minus one bc the first character is "" from the split above
                        int index = Integer.parseInt(permuteWordArray[x+1]) - 1 ; //the x + 1 bc of the "" from the split and minus one bc array starts at zero
                        if(index >= charArray.length)
                            continue;
                        char c = charArray[ index ];
                        //System.out.println("Next Permute Index: " + permuteWordArray[x+1] + " which was " + c);
                        hashVal = z*hashVal + c;
                    }
                    int hash = hashVal % TABLE;
                    if( hash < 0)
                        hash += TABLE;
                    if(array[hash] == 0)
                        array[hash] = 1;
                    else if ( hash != 0) {
                        collisions++;
                        array[hash] = array[hash] + 1;
                    }
                    //System.out.println("Word: " + next + ". \t\t\tTotal: " + hashVal + ". \t\tHash: " + hash);
                    
                    //if(debugBreakCounter++ > 3)
                        // break;
                }
                //System.out.println("Collisions for [" + permute + "] was " + collisions);
                if(collisions <= 6){
                    goodPermutations.add(permute);
                }
                if(collisions == 0){
                    zeroPermutations.add(permute);
                    list.add(z);
                }
                //for(int i = 0; i < array.length; i++){
                //    System.out.println(i + " = " + array[i]);
                //}

                //for(int i = 0; i < array.length; i++){
                //    System.out.println(i + " = " + array[i]);
                //}
                if(collisions < bestCollisions){
                    bestCollisions = collisions;
                    bestNum = z;
                }
                
                }
                if( bestCollisions < 3)
                    System.out.println("For ["+ permute +"] was " + bestNum + " with " + bestCollisions + " collisions");
        }
            System.out.println("--- Good Results for WORD_SIZE = " + WORD_SIZE + " ----");
            //System.out.println(goodPermutations);
            System.out.println(zeroPermutations);
            System.out.println(goodPermutations.size());
            System.out.println(zeroPermutations.size());
            System.out.println("PermCounter: " + permCounter);
            System.out.println("Zero Index : " + list);
            
            
            
    }
    
}
