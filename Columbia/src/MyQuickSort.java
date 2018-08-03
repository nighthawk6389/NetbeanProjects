
import java.awt.Point;
import java.util.Comparator;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mavis Beacon
 */
public class MyQuickSort {
    
    public static final int ASCENDING = 1;
    public static final int DESCENDING = 2;
    
    public static final int CUTOFF = 3;
    
    public static void main(String args []){
        
        Integer [] i = new Integer[20];
        for( int x = 0; x < i.length; x++){
            i[x] =(int) (Math.random()*100);
        }
        
        MyQuickSort.quickSort(i, 0 , i.length - 1, MyQuickSort.DESCENDING);
        
        for(Integer num : i){
            System.out.println(num);
        }
        
    }

    public MyQuickSort(){
        
    }
    
    public static void quickSort( Comparable [] p, int left, int right, int which){
        
        if( left + CUTOFF <= right){
            int pi = (which == ASCENDING) ? partitionAsc( p , left, right) : partitionDesc( p , left, right);
            quickSort( p , left, pi - 1, which);
            quickSort( p , pi + 1, right, which);
        } else {
            if(which == ASCENDING)
                insertSortAsc(p);
            else
                insertSortDesc(p);
        }
        
    }
    
    private static int partitionAsc(Comparable [] p, int left, int right){
        int pivot = median3( p , left, right);
        
        int store = left;
        for(int i = left; i < right -1; i++){
            if( p[i].compareTo(p[pivot]) <= 0)
                swapReferences( p , i , store++);
        }
        swapReferences( p , store, right);
        return store;
    }
    private static int partitionDesc(Comparable [] p, int left, int right){
        int pivot = median3( p , left, right);
        
        int store = left;
        for(int i = left; i < right -1; i++){
            if( p[i].compareTo(p[pivot]) >= 0)
                swapReferences( p , i , store++);
        }
        swapReferences( p , store, right);
        return store;
    }
    
    private static int median3(Comparable [] p, int left, int right) {
        int center = (left + right) / 2;
        if( p[center].compareTo( p[left]) < 0)
            swapReferences( p , left , center);
        if( p[right].compareTo( p[left]) < 0)
            swapReferences( p , left, right);
        if( p[right].compareTo( p[center]) < 0)
            swapReferences ( p, center, right);
        
        swapReferences( p , center, right );
        return right;
    }
    
    private static void swapReferences(Comparable[] p, int x, int y) {
        Comparable temp = p[x];
        p[x] = p[y];
        p[y] = temp;
    }
    
    public static Comparable[] insertSortAsc(Comparable [] array){
            Comparable y = array[0];
            int j=0;
            for(int x = 1; x < array.length; x++){
                    y = array[x];
                    j = x-1;
                    while(j>=0 && y.compareTo(array[j]) < 0 ){
                            array[j+1] = array[j];
                            j--;
                    }//end while
                    array[j+1]=y;
            }//end for
            return array;
    }
        
    public static Comparable[] insertSortDesc(Comparable [] array){
            Comparable y = array[0];
            int j=0;
            for(int x = 1; x < array.length; x++){
                    y = array[x];
                    j = x-1;
                    while(j>=0 && y.compareTo(array[j]) > 0 ){
                            array[j+1] = array[j];
                            j--;
                    }//end while
                    array[j+1]=y;
            }//end for
            return array;
    }

}
