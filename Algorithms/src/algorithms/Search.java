/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithms;

import java.util.*;

/**
 *
 * @author Mavis Beacon
 */
public class Search {
    private final static int tableSize=5;

    public static void main(String args []){
        Search s=new Search();

        int [] array=new int[200];
        for(int x=0;x<array.length;x++)
            array[x]=(int)(x*Math.random());

        System.out.println(s.hashSearchBeforeLoad(array,100));
    }
    public Search(){

    }

    public int binarySearch(int [] array,int index){
        int low=0;
        int high=array.length;
        int mid;
        while(low<=high){
            mid=(low+high)/2;
            if(array[mid]==index)
                return mid;
            if(index<array[mid])
                high=mid-1;
            else
                low=mid+1;
        }//end while
        return -1;
    }//end binarySearch

    public boolean hashSearchBeforeLoad(int [] array,int index){
        List [] table=loadTable(array);

        int hash=hashFunction(index);
        List a=table[hash];
        if(a==null)
            return false;
        if(a.contains(index))
            return true;
        return false;

    }//end hashSearch
    
    public boolean hashSearchBeforeLoad(List<Integer> array,int index){
        Integer [] arr=array.toArray(new Integer[0]);
        List [] table=loadTable(arr);

        int hash=hashFunction(index);
        List a=table[hash];
        if(a==null)
            return false;
        if(a.contains(index))
            return true;
        return false;

    }//end hashSearch

    public boolean hashSearchAfterLoad(List [] table,int index){

        int hash=hashFunction(index);
        List a=table[hash];
        if(a==null)
            return false;
        if(a.contains(index))
            return true;
        return false;

    }//end hashSearch

    public List[] loadTable(int [] array){
        List [] table=new List[tableSize];
        int hash;
        for(int x=0;x<array.length;x++){
            hash=hashFunction(array[x]);
            if(table[hash]==null)
                table[hash]=new ArrayList();
            table[hash].add(array[x]);
        }
        return table;
    }//end loadTable

     public List[] loadTable(Integer [] array){
        List [] table=new List[tableSize];
        int hash;
        for(int x=0;x<array.length;x++){
            hash=hashFunction(array[x]);
            if(table[hash]==null)
                table[hash]=new ArrayList();
            table[hash].add(array[x]);
        }
        return table;
    }//end loadTable

    private int hashFunction(int x){
        int hash=new Integer(x).hashCode();
        if(hash<0)
            hash=0-hash;
        return hash%tableSize;
    }//end hashFunction
}
