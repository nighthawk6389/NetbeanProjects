/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package city;

import java.util.*;

/**
 *
 * @author Mavis Beacon
 */
public class ShellQuick {
    public static int count;
    public static List<int []> list=new ArrayList<int []>();

    public static void main(String args []){
        int random=100000;

        int [] array1,array2,array3;
        array1=new int[10];
        array2=new int[200];
        array3=new int[1000];

        for(int x=0;x<array1.length;x++){
            array1[x]=(int)(Math.random()*random);
        }
        for(int x=0;x<array2.length;x++){
            array2[x]=(int)(Math.random()*random);
        }
        for(int x=0;x<array3.length;x++){
            array3[x]=(int)(Math.random()*random);
        }

        System.out.println("Array 1");
        shellSort(array1);

        System.out.println("Array 2");
        shellSort(array2);

        System.out.println("Array 3");
        shellSort(array3);
    }//end main

    public static void shellSort(int [] array){
        int inc=8;
        int temp;
        int j;
        System.out.println("Before");
        output(array);
        while(inc>0){
            for(int i=inc;i<array.length;i=i+inc){
                temp=array[i];
                j=i;
                while(j>=inc && array[j-inc] > temp){
                    array[j]=array[j-inc];
                    j=j-inc;
                }//end while
                array[j]=temp;
                //System.out.println("J="+j);
            }//end for

            System.out.println();
            System.out.println("Before Quick inc="+inc);
            output(array);
            int [] tempArray=Arrays.copyOf(array, array.length);
            int [] tempArray2=Arrays.copyOf(array, array.length);

            count=0;
            quickSort(tempArray,0,array.length-1);
            System.out.println("After Quick inc="+inc+"            Count="+count);
            output(tempArray);
            System.out.println();

            count=0;
            randomized_quickSort(tempArray2,0,array.length-1);
            System.out.println("After Randomized Quick inc="+inc+" Count="+count);
            output(tempArray2);

            inc=inc/2;

        }//end while
        System.out.println();
        System.out.println("After Shell Finishes");
        output(array);
    }//end shellsort

    public static int quickSort(int [] array,int left,int right){
        count=1;
        if(right>left){
            //int pivot=(int)(Math.random()*(right-left)+left);
            int pivot=right;
            pivot=partition(array,left,right,pivot);

            count=count+quickSort(array,left,pivot-1);
            count=count+quickSort(array,pivot+1,right);
        }//end if
        return count;
    }//end quickSort
  

        public static void randomized_quickSort(int [] array,int left,int right){
        if(right>left){
            int pivot=(int)(Math.random()*(right-left)+left);
            pivot=partition(array,left,right,pivot);

            quickSort(array,left,pivot-1);
            quickSort(array,pivot+1,right);
        }//end if
    }//end quickSort

   private static int partition(int [] array, int left,int right,int pivotIndex){

             int pivotNumber=array[pivotIndex];
            //moving pivot to right and right to pivot
            int temp=array[pivotIndex];
            array[pivotIndex]=array[right];
            array[right]=temp;

            //moving all numbers less then pivot in front of pivot
            int store=left;
            for(int x=left;x<right;x++){
                if(array[x]<=pivotNumber){
                    temp=array[x];
                    array[x]=array[store];
                    array[store]=temp;
                    store++;
                }//end if
            }//end for

            temp=array[right];
            array[right]=array[store];
            array[store]=temp;

            return store;
        }//end partition

    public static void output(int [] array){
        for(int x=0;x<array.length;x++)
            System.out.print(array[x]+" ");
        System.out.println();
    }//end array
}
