import javax.swing.JOptionPane;
public class Fibi{
    
    public static void main(String args []){
        String num1= JOptionPane.showInputDialog(null,"Get sum of X amount of fibinochi-Enter amount of X", "Enter", JOptionPane.QUESTION_MESSAGE);
        int num= Integer.parseInt(num1);
        
        JOptionPane.showMessageDialog(null,whileCompute(num),"Sum of "+ num + " fibinochi numbers", JOptionPane.INFORMATION_MESSAGE);
        
    }
    
    public static double compute(int num){
        if(num==1 || num==0)
            return num;
        else
            return compute(num-1)+ compute(num-2);
    }
    
    public static double whileCompute(int num){
        int first=0;
        int second=1;
        int temp;
        int sum=0;
        while(num>0){
            temp=first+second;
            first=second;
            second=temp;
            num--;
            sum+=first;
        }
        String s=Numbers.displayWord(sum);
        System.out.println(" Sum="+s) ;
        return sum;
            
    }
}
