package dataStructures.hw5;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Mavis Beacon
 */
public class MyHashTable {
    
    String [] table;
    int tableSize;
    
    public MyHashTable(int capacity){
        table = new String[capacity];
        tableSize = capacity;
    }
    
    public void put(String word){
        word = word.trim();
        int collision = 0;
        int hashVal = getOriginalHashCode(word);
        while( table[hashVal] != null ){
            collision++;
            hashVal++;
            if( hashVal >= tableSize)
                hashVal = 0;
        }
        table[hashVal] = word;
       // if( collision > 0)
       //    System.out.println(word + " was inserted at index " + hashVal + " with hashVal of " + getOriginalHashCode(word) + " and " + collision + " collisions");
        
    }
    
    public boolean isInTable(String word){
        word = word.trim();
        int hashVal = getOriginalHashCode(word);
        while( table[hashVal] != null){
            if( table[hashVal].equalsIgnoreCase(word))
                return true;
            hashVal++;
        }
        return false;
    }
    
    public int getOriginalHashCode(String word){
        int hash = word.trim().hashCode();
        int hashVal = hash % tableSize;
        if(hashVal < 0)
            hashVal += tableSize;
        return hashVal;
    }
}
