    /*
     * Main.java
     *
     * Created on December 15, 2009, 5:05 PM
     *
     * To change this template, choose Tools | Template Manager
     * and open the template in the editor.
     */                 

    package Sudoku;

    import javax.swing.JFrame;
                                
    /**
     *
     * @author Ilan Elkobi 
     */                          
    public class Main extends JFrame {     

        /** Creates a new instance of Main */
        public static void main(String args[]) {  
            JFrame frame=new JFrame();  
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);  
            SudokuCreator bj=new SudokuCreator();

            frame.getContentPane().add(bj); 
            frame.setSize(400,400);   
        }
    }    
