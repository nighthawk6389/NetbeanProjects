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
public class HashFunction {
    
    public static void main(String args [] ){
        
        int TABLE = 46;
        int START = 2;
        int WORD_SIZE;
        
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
        
        LinkedList<String> permutations = new LinkedList<String>();
            for(int i = 0; i < START - 1; i++){
                permutations.add("-"+(i+1)+"-");
            }
        
        
        LinkedList<String> newPermutations = new LinkedList<String>();
        int debugBreakCounter = 0;
        int array [];
        LinkedList<String> goodPermutations;
        LinkedList<String> zeroPermutations;
        ArrayList<String> wordsLessThan;
 
        
        for(int q = START; q < 13; q ++){
            
            WORD_SIZE = q;
            System.out.println("--------------------------");
            System.out.println("On WORD_SIZE = " + WORD_SIZE);

            
            goodPermutations = new LinkedList<String>();
            zeroPermutations = new LinkedList<String>();
            wordsLessThan = new ArrayList<String>();
            for(String next : listOfWords){
                if( next.length() <= WORD_SIZE )
                    wordsLessThan.add(next);
            }
            System.out.println(wordsLessThan + "\n" + wordsLessThan.size());
            
            System.out.println("Permutations Size: " + permutations.size());
            //for(int i = 0; i < WORD_SIZE  ; i++){
                //System.out.println(" i = " + i);
                while( !permutations.isEmpty() ){
                    String next = permutations.pop();
                    String [] nextArray = next.split("-");
                    //System.out.println("Next Array: " + Arrays.toString(nextArray));
                    int addToLength = 0;
                    for(int k = 0; k < WORD_SIZE ; k++){
                        for(int l = -1; l + addToLength < next.length() - 1; l = l + 2){
                            if(next.contains("-" + (k+1) + "-" ) || next.contains( "-" + (k+1) + "-" ) ){
                                //System.out.println("Next : " + next + ". Skipping with " + (k+1));
                                continue;
                            }
                            //System.out.println("Adding to " + next + " " + (k+1));
                            
                           // System.out.println("L+1/2 = " + (l+1)/2 + " L + add= " + (l+addToLength) + ". Next = "+ next);
                            if( (l+1)/2 < nextArray.length - 1 && nextArray[ (l+1)/2 ].length() > 1)
                                addToLength++;
                            
                            String post = "";
                            if( l + 2 + addToLength < next.length()){
                                post = next.substring( l + 2 + addToLength);
                            }
                            String pre = "";
                            
                            //System.out.println("Next: " + next + ". Next Length:" + next.length() + ". L = " + l);
                            //System.out.println("Pre: " + next.substring(0, l + 2) + ". K+1 = " + (k+1) +". Post: " + post);
                                newPermutations.add( next.substring(0, l + 2 + addToLength) + (k+1) + "-" + post);
                            
                        }
                       // System.out.println("new Permutations are now: " + newPermutations);
                    }
                } 
             
            permutations = newPermutations;
            newPermutations = new LinkedList<String>();
            
           // }
            System.out.println("Found " + permutations.size() + " permutations for WORD_SIZE = "  + WORD_SIZE);
            System.out.println(permutations + "\n" + permutations.size());


            //for(int i = permutations.size() - 1; i >= 0; i--){
                //String permute = permutations.get(i);//
            String minPermute = null;
            int minCollisions = 11;
            for(String permute : permutations){
                String [] permuteWordArray = permute.split("-");

                int collisions = 0;
                array = new int[TABLE];
                for(String next : wordsLessThan){
                    char [] charArray = next.toCharArray();
                    long hashVal = 0;
                    int length = permuteWordArray.length;
                    //System.out.println(Arrays.toString(permuteWordArray));
                    for(int x = 0; x < length - 1 ; x++){ // the minus one bc the first character is "" from the split above
                        int index = 0;
                        try{    
                            index = Integer.parseInt(permuteWordArray[x+1]) - 1 ; //the x + 1 bc of the "" from the split and minus one bc array starts at zero
                        }catch(Exception ex){
                            System.out.println("EXCEPTION: permute was " + permute);
                            return;
                        }
                        if(index >= charArray.length)
                            continue;
                        char c = charArray[ index ];
                        //System.out.println("Next Permute Index: " + permuteWordArray[x+1] + " which was " + c);
                        hashVal = 37*hashVal + c;
                    }
                    int hash = (int) (hashVal % TABLE );
                    if( hash < 0)
                        hash += TABLE;
                    if(array[hash] == 0)
                        array[hash] = 1;
                    else if (hash !=0 ){
                        collisions++;
                        array[hash] = array[hash] + 1;
                        //System.out.println("Collision at hash = " + hash);
                    }
                    //System.out.println("Word: " + next + ". \t\t\tTotal: " + hashVal + ". \t\tHash: " + hash);
                }
                //System.out.println("Collisions for WORD_SIZE = " + WORD_SIZE + " was " + collisions);
                if(collisions <= 6){
                    //System.out.println("Adding [" + permute + "] with " + collisions + " collisions");
                    goodPermutations.add(permute);
                    if( collisions < minCollisions){
                        //System.out.println("Setting min");
                        minCollisions = collisions;
                        minPermute = permute;
                    }
                    
                }
                if(collisions == 0){
                    zeroPermutations.add(permute);
                }
                //for(int i = 0; i < array.length; i++){
                //    System.out.println(i + " = " + array[i]);
                //}

                //if(debugBreakCounter++ > 2)
                 //   break;
            }

            System.out.println("--- Good Results for WORD_SIZE = " + WORD_SIZE + " ----");
            //System.out.println(goodPermutations);
            System.out.println(zeroPermutations);
            System.out.println(goodPermutations.size());
            System.out.println(zeroPermutations.size());
            System.out.println("Min was [" + minPermute + "] with " + minCollisions + " collisions");
            
            permutations = goodPermutations;
        }
    }
}
