import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

/*
 * NumOccur.java
 *
 * Created on May 12, 2008, 12:00 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class NumOccur extends JFrame implements ActionListener,Comparator{
    Map occur= new HashMap();
    JButton sort, shuffle, reverse;
    JTextArea area;
    JTextField field;
    String string= " ";
    
    /** Creates a new instance of NumOccur */
    public NumOccur() {
        JPanel panel = new JPanel();
        //panel.setLayout(new BorderLayout());
        JLabel label= new JLabel("Enter a number");
        panel.add(label);
        field= new JTextField(10);
        panel.add(field);
        area= new JTextArea(10,10);
        area.setEditable(false);
        panel.add(area);
        panel.add(sort=new JButton("Sort"));
        panel.add(shuffle=new JButton("Shuffle"));
        panel.add(reverse= new JButton("Reverse"));
         field.addActionListener(this);
        getContentPane().add(panel);
        setFocusable(true);
        field.requestFocusInWindow();
    }
    
    public static void main(String args []){
        NumOccur frame= new NumOccur();
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    }
    
    public void readNum(){
        Integer num=new Integer(field.getText().trim());
        string+= (num.toString()+" ");
        field.setText("");
        area.setText(string);
        field.requestFocusInWindow();
        if(num==0){
            end();
            occur.clear();
            area.setText("");
            System.out.println(string);
            string="";
        }
        else if(occur.containsKey(num)){
            int value =((Integer)(occur.get(num))).intValue();
            value++;
            occur.put(num,new Integer(value));
        }
        else
            occur.put(num,1);
    }
    
    public void end(){
        Map sort= new TreeMap(this);
        sort.putAll(occur);
        Set set = sort.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
        
    }
    
    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
        readNum();
    }

    public int compare(Object o1, Object o2) {
         int one=((Integer)(occur.get(o1))).intValue();
         int two=((Integer)(occur.get(o2))).intValue();
         
         if(one>two)
             return 1;
         else if(two>one)
             return -1;
         else{
             if(((Integer)(o1)).intValue()>((Integer)(o2)).intValue())
                 return 1;
             else
                 return -1;
         }
    }
}
        
        
    
