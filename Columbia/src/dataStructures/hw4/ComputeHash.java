package dataStructures.hw4;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mavis Beacon
 */
public class ComputeHash {
    
    public static void main(String args []){
        
        String filePath = "reservedWords.txt";
        
        boolean debug = false;
        if( args.length == 1){
            if( args[0].equals("debug"))
                debug = true;
        }
            
        
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException ex) {
               System.out.println("EXCEPTION: " + ex.toString());
            return;
        }
        
        int TABLE = 46;
        int WORD_SIZE = 12;

        
        String [] listOfWords = new String [46];
        int counter = 0;
        while(scanner.hasNext()){
            listOfWords[counter++] = scanner.next();
        }
        
            LinkedList goodPermutations = new LinkedList<String>();
            LinkedList zeroPermutations = new LinkedList<String>();
            ArrayList<String> wordsLessThan = new ArrayList<String>();
            HashMap<Integer,ArrayList<String>> map = new HashMap<Integer,ArrayList<String>>();
            for(String next : listOfWords){
                if( next.length() <= WORD_SIZE ){
                    wordsLessThan.add(next);
                }
            }
        
            if( debug )
                System.out.println(wordsLessThan);
            
            ArrayList<Integer> list = new ArrayList<Integer>();
            int bestNum = 1;
            int bestCollisions = 7;
            

            String permute = "1-10-4-8-2-7-3-11-9-12-6-5";
            if( debug )
                System.out.println("The sequence of letters is: " + permute);
            String [] permuteWordArray = permute.split("-");
            for(int z = 37; z < 38; z++){
            int debugBreakCounter = 0;
                int collisions = 0;
                int [] array = new int[46];
                for(String next : wordsLessThan){
                    char [] charArray = next.toCharArray();
                    long hashVal = 0;
                    int length = permuteWordArray.length;
                    //System.out.println(Arrays.toString(permuteWordArray) + "\n" + length);
                    for(int x = 0; x < WORD_SIZE ; x++){ // the minus one bc the first character is "" from the split above
                        int index = -1;
                        if( x < length ){
                            try{    
                                index = Integer.parseInt(permuteWordArray[x]) - 1 ; //the x + 1 bc of the "" from the split and minus one bc array starts at zero
                            }catch(Exception ex){
                                System.out.println("EXCEPTION: permute was " + permute +"\n Exception was: " + ex.toString());
                                return;
                            }
                        }
                        //if(index == charArray.length)
                        //    continue;

                        char c = '\0';
                        if( index != -1 && index < charArray.length){
                            c = charArray[ index ];
                        }
                        //System.out.println("Next C: " + c + " (index = "+(index+1)+")");
                        hashVal = 37*hashVal + c;
                    }
                    
                    //hashVal = hashVal + TABLE*next.charAt(0);
                    
                    int hash = (int) (hashVal % TABLE);
                    if( hash < 0)
                        hash += TABLE;
                   
                    if(array[hash] == 0)
                        array[hash] = 1;
                    else {//if ( hash != 0) {
                        collisions++;
                        array[hash] = array[hash] + 1;
                    }
                    
                    ArrayList mapList = map.get(hash);
                    if( mapList == null){
                        mapList = new ArrayList();
                        map.put(hash, mapList);
                    }
                    mapList.add(next);
                    
                    if( debug )
                        System.out.println("Word: " + next + ". \t\tTotal: " + hashVal + ".\t\tHash: " + hash);
                    
                    //if(debugBreakCounter++ > 3)
                        // break;
                }
                //System.out.println("Collisions for WORD_SIZE = " + WORD_SIZE + " was " + collisions);
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
                //System.out.println("Collisions for PRIME = " + z+ " was " + collisions);
                if(collisions < bestCollisions){
                    bestCollisions = collisions;
                    bestNum = z;
                }
            }
            if( debug ){
                for(Integer i : map.keySet()){
                    ArrayList mapList = map.get(i);
                        System.out.println( i + " = " + mapList );
                }
            }
            System.out.println("\n--- Good Results for WORD_SIZE = " + WORD_SIZE + " ----");
            System.out.println("Permutation: " + permute);
            System.out.print("\nHash function: ");
            String polynomialString = "";
            for(int i = permuteWordArray.length ; i > 0; i--){
                polynomialString += "[Letter #"+permuteWordArray[12 - i]+"]*37^"+i+" + ";
            }
            System.out.println(polynomialString + "0");
            
            System.out.println("\nThis hash function had  " + bestCollisions + " collisions:");
            
            for(Integer i : map.keySet()){
                ArrayList mapList = map.get(i);
                if( mapList.size() > 1)
                    System.out.println( "Index " + i + " has " + mapList.size() + " words " +
                            "\t" + mapList);
            }
            //System.out.println(goodPermutations);
            //System.out.println(zeroPermutations);
            //System.out.println(goodPermutations.size());
            //System.out.println(zeroPermutations.size());
            //System.out.println("Best Prime Num: " + bestNum + " with " + bestCollisions + " collisions");
            
        
    }
    
}
