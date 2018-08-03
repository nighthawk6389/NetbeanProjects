/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package city;

/**
 *
 * @author Mavis Beacon
 */
public class SelectionSortModified {

    public static void main(String args []){
        int keyL;
        int keyH;
        int lowest;
        int highest;
        //Counts amount of comparisons
        int counter=0;
        int smaller,larger;
        int odd=0;


        //Initializing the array with random values
        int [] array=new int [1000];
        System.out.println("Unsorted");
        for(int x=0;x<array.length;x++){
            array[x]=(int)(Math.random()*10000);
            System.out.print(array[x]+" ");
        }
        System.out.println();

        //Begin outer loop
        for(int x=0;x<array.length/2+odd;x++){
            lowest=array[x];
            highest=array[array.length-1-x];
            keyL=x;
            keyH=x;

            //Odd amount of numbers
            if(x==0 && array.length%2!=0){
                lowest=highest=array[0];
                odd=1;
            }
            //Begin inner loop
            for(int y=x+odd;y<array.length-1-x+odd;y=y+2){

                //Intialize smaller and larger.
                smaller=y;
                larger=y+1;
                
                counter++;
                //Check if smaller and larger are true. If not, reverse them
                if(array[y]>array[y+1]){
                    smaller=y+1;
                    larger=y;
                }

                counter++;
                //Check if smaller is less then lowest.If so, store key and number
                if(array[smaller]<lowest){
                    lowest=array[smaller];
                    keyL=smaller;
                }//end if

                counter++;
                //Check if larger is greater then highest.If so, store key and number
                if(array[larger]>highest){
                    highest=array[larger];
                    keyH=larger;
                }//end if

            }//end inner for loeep

            //Check if lowest isnt the current x. If not, exchange them
            if(lowest!=array[x]){
                array[keyL]=array[x];
                array[x]=lowest;
            }//end if

            //This is a check to make sure that array[x] that was just moved in
            //the previous if statement wasnt the highest number. If it was,
            //then the keys need to be switched. This checks hurts the constants
            //but keeps overall O(n^2)
            if(array[keyL]==highest){
                System.out.println("Saved");
                keyH=keyL;
            }

            ////Check if highest is the current array.legnth-1-x. If not, exchange them
            if(highest!=array[array.length-1-x]){
                array[keyH]=array[array.length-1-x];
                array[array.length-1-x]=highest;
            }//end if

            System.out.println("Counter so far: "+counter);
        }//end ofr

        //Outputting sorted array
        System.out.println();
        System.out.println("Sorted");
        for(int x=0;x<array.length;x++){
            System.out.print(array[x]+" ");
        }
        System.out.println();

    }//end static
}//end selectionSort
