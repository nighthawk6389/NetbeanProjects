
package acm;

import java.util.*;
import java.io.*;

public class EqualSums {

    public static void main(String args []){
        Scanner s;
        String [] answers;

        int dataSets;
        int setNumber;
        int numIntegers;

        s=new Scanner(new BufferedInputStream(System.in));
        dataSets=s.nextInt();
        System.out.println("DataSets: "+dataSets);
        
        answers=new String[dataSets];

        for(int x=0;x<dataSets;x++){
            s=new Scanner(new BufferedInputStream(System.in));
            setNumber=s.nextInt();
            numIntegers=s.nextInt();
            System.out.println("Num:"+numIntegers);

            int y=1;
            int [] array=new int[numIntegers];
            while(s.hasNext() && y<numIntegers){
                if(s.hasNextInt())
                    array[y-1]=s.nextInt();
                else
                    s.next();
                y++;
            }//end while
            array[y-1]=s.nextInt();

            for(int i:array)
                System.out.println(i);

            int sum1=0;
            int sum2=0;
            int tempSum=0;
            for(int z=0;z<numIntegers;z++){
                sum1=0;
                System.out.println("Begining");
                for(int a=0;a<z+1;a++){
                    sum1=sum1+array[a];
                }//end for
                int track=z+1;
                System.out.println("Sum1="+sum1+" track="+track);
                while(track<numIntegers){
                    while(tempSum<sum1 && track<numIntegers){
                        tempSum=tempSum+array[track++];
                        System.out.println("TempSum="+tempSum);
                    }//end while
                    System.out.println("TempSum="+tempSum+" track="+track);
                    if(tempSum==sum1){
                        System.out.println("Good Sum");
                    }
                    else{
                        System.out.println("Bad Sum");
                        tempSum=0;
                        sum2=0;
                        break;
                    }
                    sum2=tempSum;
                    tempSum=0;
                }//end while
                if(sum1==sum2)
                    break;
            }//end for

            answers[x]=setNumber+" "+sum1;
        }//end for
        
            
            for(String st:answers)
                System.out.println(st);
/*
3
1 6
2 5 1 3 3 7
2 6
1 2 3 4 5 6
3 20
1 1 2 1 1 2 1 1 2 1
1 2 1 1 2 1 1 2 1 1
*/


    }//end main

}
