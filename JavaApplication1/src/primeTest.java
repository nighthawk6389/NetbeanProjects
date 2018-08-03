import javax.swing.JOptionPane;
/*
 * primeTest.java
 *
 * Created on September 23, 2007, 12:13 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class primeTest {
    
    /** Creates a new instance of primeTest */
    public primeTest() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String prime1= JOptionPane.showInputDialog(null,"Enter amount of num","Enter",JOptionPane.QUESTION_MESSAGE);
        double prime=Double.parseDouble(prime1);
        
        JOptionPane.showMessageDialog(null,primeTest(prime),"Prime Numbers",JOptionPane.INFORMATION_MESSAGE);
    }
    public static String primeTest(double prime){
        String finalP = "";
        boolean is=true;
        int y=0;
        
        while(prime>=0){
            is=true;
            for(int x=2;x<=prime/2;x++){
                if(prime%x==0){
                    is=false;
                    break;
                }
            }
            if(is==true){
                if(y>=4){
                finalP+= (int)prime+" \n";
                y=0;
                }
                else
                    finalP+=(int)prime+ " ";
                y++;
            }
            
            //System.out.println(finalP);
            
           prime--;
        }
        return finalP;
        
    }
    
}
