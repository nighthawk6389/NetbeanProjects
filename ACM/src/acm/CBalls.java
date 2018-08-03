/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package acm;

import java.io.*;
import java.util.*;

public class CBalls {

    public static void main(String args []){
        Scanner s;

        int balls;
        int floors;
        int numFloorsMove;
        int totalMoves;
        int localShort;
        int shortest=100;
        int SNM=100;

        s=new Scanner(new BufferedInputStream(System.in));
        balls=s.nextInt();
        floors=s.nextInt();
        System.out.println("Balls:"+balls+" Floors:"+floors);

        for(int x=1;x<=floors;x++){
            totalMoves=0;
            numFloorsMove=x;
            System.out.println("X="+x);
            for(int y=numFloorsMove;y<=floors;y=y+numFloorsMove){
                totalMoves=totalMoves+1;
                System.out.println("TotalMoves="+totalMoves+" Y="+y);
            }//end for
            int addition=((floors%numFloorsMove)>(numFloorsMove-1)?floors%numFloorsMove:(numFloorsMove-1));
            totalMoves=totalMoves+addition;
            System.out.println("Total="+totalMoves+" addition="+addition);
            if(totalMoves>numFloorsMove)
                localShort=totalMoves;
            else
                localShort=numFloorsMove;

            System.out.println("LS="+localShort);

            if(localShort<shortest){
                shortest=localShort;
                SNM=numFloorsMove;
            }
            System.out.println("Shortest="+shortest+" SNM="+SNM);
        }//end for
    }//end main

}
