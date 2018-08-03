import java.io.*;
import java.util.*;
public class Phone{
    public static void main(String args []) throws FileNotFoundException, IOException{
        Scanner in = new Scanner(new BufferedReader(new FileReader("c:/phone.txt")));
        double sum=0;
        double minutes=0;
        while(in.hasNext()){
           if(in.hasNextDouble()){
               in.next();
                minutes+=in.nextDouble();
                sum+=in.nextDouble();
                System.out.println("minutes: "+minutes+"\n"+sum);
            }
           in.next();
        }
        System.out.println("Total Minutes: "+minutes+"\n"+"Total Cost: "+sum);
    }
}
