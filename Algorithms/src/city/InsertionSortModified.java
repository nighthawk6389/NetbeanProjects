/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package city;

/**
 *
 * @author Mavis Beacon
 */
public class InsertionSortModified {

    public static void main(String args []){

        //Initialize main array with random numbers and output it
        int [] array=new int [10];
        System.out.println("Unsorted");
        for(int x=0;x<array.length;x++){
            array[x]=(int)(Math.random()*100);
            System.out.print(array[x]+" ");
        }


        int j;
        //stores the number sent to binaryseach
        int key;
        //stores the position that binarysearch returns
        int index;
        for(int x=0;x<array.length;x++){
            key=array[x];
            index=binarySearch(array,key,x);
            j=x-1;
            while(j>=index){
                array[j+1]=array[j];
                j--;
            }
            array[j+1]=key;

        }//end outer for loop

        for(int y=0;y<array.length;y++){
            System.out.print(array[y]+" ");
        }
        System.out.println();


    }//end main

    public static int binarySearch(int [] array, int key, int high){
        //Binary search needs to be modified from its original form because it
        //isnt finding a value but is looking for a place between 2 keys.

        int low=0;
        int mid=0;
        int place=0;
        while(low<=high){
            mid=(low+high)/2;

            //Checks to see if mid is not 0 or array.length and then checks if
            //the key fits in in between mid and mid-1. Needed because the other
            //2 checks continue mid-1 or mid+1.
            //The place is used as to store the actual index to return while
            //the high and low are used to further along the while loop
            if(mid>0 && mid<array.length-1 && key<array[mid] && key>array[mid-1])
                return mid;
            if(key<array[mid]){
                place=mid;
                high=mid-1;
            }
            else{
                place=mid;
                low=mid+1;
            }
        }//end while
       System.out.println("Returning place: "+place+". L:"+low+" H:"+high+" M:"+mid);
        return place;
    }//end binarySearch
}//end class
