                                                      /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
      
package cardGames;                  
  
import javax.swing.JFrame;
/**  
 *
 * @author Mavis Beacon                      
 */
public class Main {                        
                      
    /**        
     * @param args the command line arguments
     */
    public static void main(String[] args) {    
        // TODO code application lgic here       
        JFrame frame=new JFrame();
        frame.setSize(800,400);    
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Blackjack bj=new Blackjack();

        frame.getContentPane().add(bj);      
        
        char [] array = new char[4];
        array[0] = 'd';
        array[1] = 'h';
        array[2] = 'c';
        array[3] = 's';
        int count = 0;
        for(char c: array){
            for(int x = 1; x  <= 10; x++){
                System.out.println("images["+count+"] = ImageIO.read(new File(\"card_images/"+c+""+x+".gif\"));");
                count++;
            }
            System.out.println("images["+ count++ +"] = ImageIO.read(new File(\"card_images/"+c+"j.gif\"));");
            System.out.println("images["+ count++ +"] = ImageIO.read(new File(\"card_images/"+c+"q.gif\"));");
            System.out.println("images["+ count++ +"] = ImageIO.read(new File(\"card_images/"+c+"k.gif\"));");
        }
    }     

}
