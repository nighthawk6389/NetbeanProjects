package dataStructures.hw4;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
public class NewHashFunction {
    
    public static void main(String args []){
        
        int TABLE = 46;
        int START = 2;
        int WORD_SIZE;
        int MAX_WORD_LENGTH = 12;
        
        String filePath = "reservedWords.txt";

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException ex) {
            System.out.println("EXCEPTION: " + ex.toString()); 
            return;
        }
        
        
        String [] listOfWords = new String [46];
        int counter = 0;
        while(scanner.hasNext()){
            listOfWords[counter++] = scanner.next();
        }
        
        LinkedList<String> permutations = new LinkedList<String>();
        StringBuffer nextBuf = new StringBuffer();
        //nextBuf.append("-");
            for(int i = 0; i < MAX_WORD_LENGTH; i++){  //i < START - 1
                nextBuf.append("0-");
            }
            StringBuffer temp;
            for(int i = 0; i < nextBuf.length() / 2; i++){
                temp = new StringBuffer(nextBuf);
                temp.setCharAt(i * 2, '1');
                permutations.add(temp.toString());
            }
            System.out.println(permutations.size());
        
        
        LinkedList<String> newPermutations = new LinkedList<String>();
        int debugBreakCounter = 0;
        int array [];
        LinkedList<String> goodPermutations;
        LinkedList<String> zeroPermutations;
        ArrayList<String> wordsLessThan;
        long [] remember = new long[46];
        long [] originalHash = new long[46];
        String [] word = new String[46];
        String [] originalPerm = new String[46];
 
        String minPermute = null;
        int minCollisions = 11;
        
        for(int q = START; q < 13; q ++){
            
            WORD_SIZE = q;
            System.out.println("--------------------------");
            System.out.println("On WORD_SIZE = " + WORD_SIZE);

            minCollisions = 11;
            minPermute = null;
            
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
                    String nextPermute = permutations.pop();
                    //String [] nextArray = nextPermute.split("-");
                    //System.out.println("Next Array: " + Arrays.toString(nextArray));
                     for(int k = 0; k < q ; k++){
                        for(int l = 0; l < nextPermute.length() ; l++){
                            if(nextPermute.contains("-" + (k+1) + "-" ) || nextPermute.contains( (k+1) + "-" ) ){
                                //System.out.println("Next : " + next + ". Skipping with " + (k+1));
                                break;
                            }
                            StringBuffer tempBuf = new StringBuffer(nextPermute); 
                            if( tempBuf.charAt(l) != '0' ||  ( (l-1) >= 0 && tempBuf.charAt(l-1) != '-') )
                                continue;
                            
                            tempBuf.delete(l, l+2);
                            tempBuf.insert(l, (k+1) + "-"); 
                            //newPermutations.add( tempBuf.toString() );
                            
                            String permute = tempBuf.toString();
                            
                            //System.out.println(permute);
                                                        
                            String [] permuteWordArray = permute.split("-");

                            int collisions = 0;
                            array = new int[TABLE];
                            int numWord = 0;
                            for(String next : wordsLessThan){
                                char [] charArray = next.toCharArray();
                                long hashVal = 0;
                                int length = permuteWordArray.length;
                                //System.out.println(Arrays.toString(permuteWordArray));
                                for(int x = 0; x < MAX_WORD_LENGTH ; x++){ // the minus one bc the first character is "" from the split above
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
                                    //System.out.println("Next C: " + (int)c);
                                    hashVal = 37*hashVal + c;
                                }
                                int hash = (int) (hashVal % TABLE );
                                if( hash < 0) 
                                    hash += TABLE;
                                if(array[hash] == 0)
                                    array[hash] = 1;
                                else {//if (hash !=0 ){
                                    collisions++;
                                    array[hash] = array[hash] + 1;
                                    //System.out.println("Collision at hash = " + hash);
                                }
                                //System.out.println("Word: " + next + ". \t\t\tTotal: " + hashVal + ". \t\tHash: " + hash);
                                remember[ numWord ] = hash;
                                originalHash[ numWord] = hashVal;
                                word[ numWord ] = next; 
                                numWord++;
                                
                            }
                            //System.out.println("Collisions for WORD_SIZE = " + WORD_SIZE + " was " + collisions);
                            if(collisions <= 5  ){
                                //System.out.println("Adding [" + permute + "] with " + collisions + " collisions");
                                goodPermutations.add(permute);
                                if( q == 12)
                                    System.out.println(permute + " with " + collisions + "collisions");
                                if( collisions < minCollisions){
                                    //System.out.println("Setting min");
                                    minCollisions = collisions;
                                    minPermute = permute;
                                }

                            }
                            if(collisions == 0){
                                zeroPermutations.add(permute); 
                                if( q >= 6){
                                    System.out.println("ZERO: "  + permute);
                                    
                                }
                                
                            }
                          
                            /*
                            if( q == 12 && collisions == 4 ){
                                for(int i = 0; i < array.length; i++){
                                    System.out.println(i + " = " + array[i]);
                                }
                                for(int i = 0; i < remember.length; i++){
                                    System.out.println(i + " = " + remember[i] + "  [ "+word[i]+" = "+originalHash[i]+" ]");
                                }
                            }
                             * *
                             */
                            //for(int i = 0; i < array.length; i++){
                            //    System.out.println(i + " = " + array[i]);
                            //}

                            //if(debugBreakCounter++ > 2)
                             //   break;

                        }
                                  // System.out.println("new Permutations are now: " + newPermutations);
                     }
                } 

                //permutations = newPermutations;
                //newPermutations = new LinkedList<String>();

           // }
           // System.out.println("Found " + permutations.size() + " permutations for WORD_SIZE = "  + WORD_SIZE);
            //System.out.println(permutations + "\n" + permutations.size());
            
            
          /*  
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
                    for(int x = 1; x < MAX_WORD_LENGTH; x++){ // the minus one bc the first character is "" from the split above
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
                        //System.out.println("Next C: " + (int)c);
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
                    //goodPermutations.add(permute);
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
           * 
           */

            System.out.println("--- Good Results for WORD_SIZE = " + WORD_SIZE + " ----");
            //System.out.println(goodPermutations);
            //System.out.println(zeroPermutations);
            System.out.println("Good Perms: " + goodPermutations.size());
            System.out.println("Zero Perms: " + zeroPermutations.size());
            System.out.println("Min was [" + minPermute + "] with " + minCollisions + " collisions");
            
            permutations = goodPermutations;
            if( zeroPermutations.size() == 0)
                permutations = goodPermutations;
       
        
        }
    
    }
}
