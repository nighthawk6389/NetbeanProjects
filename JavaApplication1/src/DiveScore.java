import java.util.Arrays;
public class DiveScore{
    public static void main(String args []){
        long start=System.nanoTime();
        double score []=new double[7];
        for(int x=0;x<score.length;x++){
            score[x]=Math.random()*10;
        }
        double sum=0;
        Arrays.sort(score);
        for(int x=1;x<score.length-2;x++){
            sum+=score[x];
        }
        sum=sum*(Math.random()*2.6+1.2);
        sum=sum*.6;
        long end=System.nanoTime();
        System.out.println((int)sum +" "+(end-start)/1000);
    } 
}
