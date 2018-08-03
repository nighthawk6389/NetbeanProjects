package acm;

import java.io.*;
import java.util.*;

public class Nth_Largest {


    public static void main(String args []){

        int amountSets;
        Scanner s;
        final int AMOUNT_NUMBERS=10;
        int order;

        s= new Scanner(new BufferedInputStream(System.in));
        amountSets=s.nextInt();
        String [] answers=new String[amountSets];
            int array[]=new int[AMOUNT_NUMBERS];
            for(int x=0;x<amountSets;x++){
                s= new Scanner(new BufferedInputStream(System.in));
                if(s.hasNextInt())
                    order=s.nextInt();
                else{
                    System.out.println("Incorrect input");
                    return;
                }

                int y=1;

                    while(s.hasNext() && y<AMOUNT_NUMBERS){
                        if(s.hasNextInt())
                            array[y]=s.nextInt();
                        else
                            s.next();
                    y++;
                    }//end while

                Arrays.sort(array);
                answers[x]=order+" "+array[AMOUNT_NUMBERS-2];
            }//end for

            for(String ans:answers){
                System.out.println(ans);
            }

    }//end main

}
