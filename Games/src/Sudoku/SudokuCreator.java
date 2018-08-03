                                              /*
 * SudokuCreator.java
 *
 * Created on December 8, 2009, 5:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Sudoku;      

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author elkobi
 */
public class SudokuCreator extends JPanel implements Runnable,ActionListener {
    final int n = 3;
    int[][] field = new int[n*n][n*n];
    int x = 0;
    JButton solve,clear,create;
    JLabel time;
    JTextField timeText;
    JPanel panel;

    /** Creates a new instance of SudokuCreator */
    public SudokuCreator() {
        this.setLayout(new BorderLayout());
        
        panel=new JPanel();
        panel.setLayout(new GridLayout(9,9));
        
        for(int i=0;i<field.length;i++){
            for(int j=0;j<field.length;j++){
                JTextField temp=new JTextField(1);
                temp.setFont(new Font(Font.SANS_SERIF,0,20));  
                temp.setColumns(1);
                temp.setHorizontalAlignment(JTextField.CENTER);
                panel.add(temp);
            }
        }

        JPanel panel2=new JPanel();
        solve=new JButton("Solve");
        solve.addActionListener(this);
        clear=new JButton("Clear");
        clear.addActionListener(this);
        create=new JButton("Create");
        create.addActionListener(this);
        time=new JLabel("Time Took: ");
        timeText=new JTextField(5);
        timeText.setEditable(false);
        panel2.add(solve);
        panel2.add(clear);
        panel2.add(create);
        panel2.add(time);
        panel2.add(timeText);

        this.add(panel,"Center");
        this.add(panel2,"South");
        //new Thread(this).start();
    }//end build sudokuCreator
    
    public static void main(String args []){
        SudokuCreator s=new SudokuCreator();
    }

    public void run() {
        for(int i=0;i<n;i++,x++){
            for(int j=0;j<n;j++,x+=n){
                for(int k=0;k<n*n;k++,x++){
                    field[n*i+j][k] = (x % (n*n)) + 1;
                    //System.out.println("Number is "+field[n*i+j][k]);
                    //System.out.println("I= "+i+" J= "+j+" K="+k+" X="+x);
                    //System.out.println("field[n*i+j]= "+(n*i+j));
                   /*
                    try {                         
                            Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    **/
                }//end k
            }//end j
        }//end i
         
        print();
        
        boolean valid=SudokuSolver.checkValid(field);
        System.out.println("Solver says "+valid);

        int[][]temp=field.clone();
        for(int i=0;i<temp.length;i++){
            for(int j=0;j<temp.length;j++){
            //temp[(int)(Math.random()*temp.length)][(int)(Math.random()*temp.length)]=0;
            temp[i][j]=0;
            }
        }
         print(temp);
         valid=SudokuSolver.checkValid(field);

         //System.out.println("Valid is "+valid);
         SudokuSolver.solvePuzzle(temp);                  
    }//end run
    
    public void print(){
        for(int i=0;i<field.length;i++){
            System.out.print("|");
            for(int k=0;k<field[i].length;k++){
                if(field[i][k]!=0)
                    System.out.print(field[i][k]+"|");
                else
                    System.out.print(" |");
            }
            System.out.println();
        }                             
    }//end print
    public void print(int [] [] temp){
        for(int i=0;i<temp.length;i++){
            System.out.print("|");
            for(int k=0;k<temp[i].length;k++){
                if(temp[i][k]!=0)
                    System.out.print(temp[i][k]+"|");
                else
                    System.out.print(" |");
            }
            System.out.println();
        }
    }//end print

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==solve){
            solve();
        }//end if
        else if(e.getSource()==clear){
           clearScreen();
        }//end else if
        else if(e.getSource()==create){
           createRandom();
        }//end else if
    }

    private int[][] getField() {
        int[][]temp=new int[n*n][n*n];
       // for(int x=0;x<100;x++){
            //System.out.println(panel.getComponent(x));
       // }
        Component [] texts=panel.getComponents();
        int i=0;
        for(int x=0;x<field.length;x++){
            for(int y=0;y<field.length;y++){
                JTextField text=(JTextField)texts[i];
                if(text.getText()!=null && text.getText().length()==1){
                    int num=Integer.parseInt(text.getText());
                    System.out.println("Num= "+num);
                    temp[x][y]=num;
                }//end if
                else
                    temp[x][y]=0;
                i++;
            }
            
        }
        return temp;
    }
    
    public void setFieldGUI(boolean set) {
        Component [] texts=panel.getComponents();
        int i=0;
        for(int x=0;x<field.length;x++){
            for(int y=0;y<field.length;y++){
                JTextField text=(JTextField)texts[i];
                if(field[x][y]!=0 || set)
                    text.setText(field[x][y]+"");
                i++;
            }
        }
        repaint();
    }

    private void clearScreen() {
        field=new int[n*n][n*n];
         Component [] texts=panel.getComponents();
        int i=0;
        for(int x=0;x<field.length;x++){
            for(int y=0;y<field.length;y++){
                JTextField text=(JTextField)texts[i];
                text.setText("");
                i++;
            }
        }
        repaint();
    }

    private void createRandom() {
        clearScreen();
        for(int i=0;i<field.length*3;i++){
            int one=(int)(Math.random()*field.length);
            int two=(int)(Math.random()*field.length);
            field[one][two]=(int)(Math.random()*9);
            if(!SudokuSolver.checkValid(field)){
                field[one][two]=0;
                i--;
            }
        }
        if(SudokuSolver.checkValid(field))
            System.out.println("Good create");
            setFieldGUI(false);
    }

    private void solve() {
        field=getField();
        print(field);
        System.out.println("Going into solve");
        long first=System.currentTimeMillis();
        field=SudokuSolver.solvePuzzle(field);
        long second=System.currentTimeMillis();
        long took=(second-first)/1000;
        timeText.setText(took+" ");
        System.out.println("Coming out of solve");
        boolean solved=true;
        for(int i=0;i<field.length;i++){
            for(int j=0;j<field.length;j++){
                if(field[i][j]==0)
                    solved=false;
            }
        }
        if(solved==false)
            JOptionPane.showMessageDialog(null, "The puzzle was not able to be solved");
        setFieldGUI(true);
        print(field);
    }
    
}//end class
